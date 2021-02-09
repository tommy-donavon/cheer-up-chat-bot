import numpy
import json

from bot import Bot
from nltk_utils import tokenize, stem, word_bag
from tensorflow.python.framework import ops


with open("intents.json") as f:
    data = json.load(f)

    all_data_words = []
    data_tags = []
    doc_words = []
    doc_tags = []

    banned_chars = ['?', '!', '.', ',']

    for intent in data['intents']:
        for pattern in intent['patterns']:
            words = tokenize(pattern)
            all_data_words.extend(words)
            doc_words.append(words)
            doc_tags.append(intent['tag'])
        if intent['tag'] not in data_tags:
            data_tags.append(intent['tag'])
    
    all_data_words = [stem(w.lower()) for w in all_data_words if w not in banned_chars]
    all_data_words = sorted(list(set(all_data_words)))

    data_tags = sorted(data_tags)

    training = []
    output = []

    out_default = [0 for _ in range(len(data_tags))]

    for x, doc in enumerate(doc_words):
        bag = [0 for _ in all_data_words]
        b_words = [stem(w.lower()) for w in doc]
        for word in all_data_words:
            if word in b_words:
                bag[x] = 1
        
        output_row = out_default[:]
        output_row[data_tags.index(doc_tags[x])] = 1

        training.append(bag)
        output.append(output_row)
    
    data = {"training": training, "output": output}
    with open("data.json", "w") as save_data:
        json.dump(data, save_data)


    training = numpy.array(training)
    output = numpy.array(output)

    ops.reset_default_graph()



    model = Bot(training=training, output=output)
    model.model.fit(training, output, n_epoch=10, batch_size=8, show_metric=True)
    model.model.save("model.tflearn")


    


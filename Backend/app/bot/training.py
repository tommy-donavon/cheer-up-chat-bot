import numpy
import json

from bot import Bot
from nltk_utils import tokenize, stem
from tensorflow.python.framework import ops

#import training data and add them to corresponding lists
with open("intents.json") as f:
    data = json.load(f)

    all_data_words, data_tags, doc_words, doc_tags = [], [], [], []

    banned_chars = ['?', '!', '.', ',']

    for intent in data['intents']:
        for pattern in intent['patterns']:
            words = tokenize(pattern)
            all_data_words.extend(words)
            doc_words.append(words)
            doc_tags.append(intent['tag'])
        if intent['tag'] not in data_tags:
            data_tags.append(intent['tag'])
    
    # Stem words and sort them
    all_data_words = [stem(w.lower()) for w in all_data_words if w not in banned_chars]
    all_data_words = sorted(list(set(all_data_words)))

    data_tags = sorted(data_tags)

    training = []
    output = []

    out_default = [0 for _ in range(len(data_tags))]

# Arrange data for training the bot
    for x, doc in enumerate(doc_words):
        bag = [0 for _ in range(len(all_data_words))]
        b_words = [stem(w.lower()) for w in doc]
        for index, word in enumerate(all_data_words):
            if word in b_words:
                bag[index] = 1
        
        output_row = out_default[:]
        output_row[data_tags.index(doc_tags[x])] = 1

        training.append(bag)
        output.append(output_row)
    
    #Save processed data to json file for later use
    data = {"training": training, "output": output, "all_words": all_data_words, "tags": data_tags}
    with open("data.json", "w") as save_data:
        json.dump(data, save_data)


    training = numpy.array(training)
    output = numpy.array(output)

    ops.reset_default_graph()


    #Load training data to bot
    model = Bot(training=training, output=output)
    #Shows training data to bot a repeated number of time. n_epoch refers to the amount of times the data is shown.
    model.model.fit(training, output, n_epoch=1000, batch_size=8,show_metric=True)
    #Saves model to it's own file
    model.model.save("model.tflearn")
    
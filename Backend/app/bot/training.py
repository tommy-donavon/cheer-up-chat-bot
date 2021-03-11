import numpy
import json
from bot import Bot
from NLU import tokenize, stem, word_bag
from tensorflow.python.framework import ops

#import training data and add them to corresponding lists
with open("intents.json") as f:
    intents = json.load(f)

all_data_words, data_tags, word_and_tags = [], [], []
exclude_chars = ['?', '!', '.', ',']


for intent_class in intents['intents']:
    
    data_tags.append(intent_class['tag'])
    
    for pattern in intent_class['patterns']:
        
        words = tokenize(pattern)
        all_data_words.extend(words)
        word_and_tags.append((words, intent_class['tag']))
    
# Stem words and sort them
all_data_words = [stem(w) for w in all_data_words if w not in exclude_chars]
all_data_words = sorted(set(all_data_words))
data_tags = sorted(set(data_tags))

training_data, output_data = [], []

# Stage training data
out_default = [0 for _ in range(len(data_tags))]

for(pattern, tag) in word_and_tags:
    bag = word_bag(pattern, all_data_words)
    training_data.append(bag)
    output_row = out_default[:]
    out_index = data_tags.index(tag)
    output_row[out_index] = 1
    output_data.append(output_row)

#Save processed data to json file for later use
data = {"training": training_data, "output": output_data, "all_words": all_data_words, "tags": data_tags}
with open("data.json", "w") as save_data:
    json.dump(data, save_data)


training_data = numpy.array(training_data)
output_data = numpy.array(output_data)

ops.reset_default_graph()


# Load training data to bot
model = Bot(training=training_data, output=output_data)

# Shows training data to bot a repeated number of times. n_epoch refers to the amount of steps the process will take.
model.model.fit(training_data, output_data, n_epoch=1000, batch_size=8,show_metric=True)

# Saves model to it's own file
model.model.save("model.tflearn")

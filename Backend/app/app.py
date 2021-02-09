from flask import Flask, request, jsonify, make_response
from bot.bot import Bot
from bot import nltk_utils
from pathlib import Path
import json
import numpy
import random


app = Flask(__name__)

data_path = Path(__file__).parent / "./bot/data.json"
bot_path = Path(__file__).parent / "./bot/model.tflearn"
json_path = Path(__file__).parent / "./bot/intents.json"

with json_path.open() as j:
    intent_data = json.load(j)

with data_path.open() as f:
    data = json.load(f)
    training = numpy.array(data['training'])
    output = numpy.array(data['output'])
    all_words = data['all_words']
    tags = data['tags']


model = Bot(training=training, output = output)
model.model.load(bot_path)



@app.route('/', methods=['POST'])
def bot_response():
    raw_data = request.json
    if 'message' in raw_data:
        user_input = nltk_utils.tokenize( raw_data['message'] )
        user_bag = nltk_utils.word_bag(user_input, all_words)
        results = model.model.predict([user_bag])
        matching_index = numpy.argmax(results)
        tag = tags[matching_index]
        responses = []
        for t in intent_data['intents']:
            if t['tag'] == tag:
                responses = t['responses']
        return jsonify({"bot": random.choice(responses)}), 200
        

    else:
        return jsonify({"status":"Bad_Request"}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0')
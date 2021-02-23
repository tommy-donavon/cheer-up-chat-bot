import json
import numpy
import random

from flask import Flask, request, jsonify, make_response
from bot.bot import Bot
from bot import nltk_utils
from pathlib import Path


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

follow_up_responses = ["I didn't quite get that.", "Sorry what was that?", "uhh what was that.", "Can you repeat that?"]



@app.route('/', methods=['POST'])
def bot_response():
    raw_data = request.json
    if 'message' in raw_data:
        user_input = nltk_utils.tokenize( raw_data['message'] )
        user_bag = nltk_utils.word_bag(user_input, all_words)
        results = model.model.predict([user_bag])
        props = numpy.max(results)
        matching_index = numpy.argmax(results)
        tag = tags[matching_index]
        responses = []
        if props >= .75:
            for t in intent_data['intents']:
                if t['tag'] == tag:
                    responses = t['responses']
            return jsonify({"bot": random.choice(responses), "chance": f"{props}"}), 200
        else: 
            return jsonify({"bot":random.choice(follow_up_responses), "chance": f"{props}"}), 200
        

    else:
        return jsonify({"status":"Please include a message"}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0')
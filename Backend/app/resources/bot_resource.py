import json
import numpy
import random
import os

from flask import request
from bot.bot import Bot
from bot import nltk_utils
from authentication import auth
from flask_restful import Resource


cwd = os.getcwd()

# loads in necessary data to load bot
with open(cwd + "/app/bot/data.json") as data:
    data = json.load(data)
    training = numpy.array(data['training'])
    output = numpy.array(data['output'])
    all_words = data['all_words']
    tags = data['tags']

with open(cwd + "/app/bot/intents.json") as j:
    intent_data = json.load(j)


model = Bot(training=training, output = output)
model.model.load(cwd + "/app/bot/model.tflearn")

follow_up_responses = ["I didn't quite get that.", "Sorry what was that?", "uhh what was that.", "Can you repeat that?"]

class Bot_Resource(Resource):

    @auth.login_required
    def post(self):
        """Listens for incoming posts request
    and processes properly formated messages.
    Properly formated messages are returned with
    the bots response and a 200 status code.
    Improperly formated responses are sent back
    with a 400 status code.
    """
   
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
                return {"bot": random.choice(responses), "chance": f"{props}"}, 200
            else: 
                return {"bot":random.choice(follow_up_responses), "chance": f"{props}"}, 200
            

        else:
            return {"status":"Please include a message"}, 400
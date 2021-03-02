import json
import numpy
import random

import base64

from mongoengine.errors import DoesNotExist, ValidationError
from flask_httpauth import HTTPBasicAuth
from werkzeug.security import generate_password_hash, check_password_hash
from flask import Flask, request, jsonify
from bot.bot import Bot
from bot import nltk_utils
from pathlib import Path
from mongoengine import connect, Document, StringField

app = Flask(__name__)
auth = HTTPBasicAuth()

DB_URI = 'mongodb+srv://User:TWKUDY8qLS2Db0hE@cluster0.a8rpi.mongodb.net/se?retryWrites=true&w=majority'

db = connect(host=DB_URI)

class User(Document):
    name = StringField(required=True, unique=True)
    password = StringField(required=True)



# Loads in necessary files to reload the bot after training is complete
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
            return jsonify({"bot": random.choice(responses), "chance": f"{props}"}), 200
        else: 
            return jsonify({"bot":random.choice(follow_up_responses), "chance": f"{props}"}), 200
        

    else:
        return jsonify({"status":"Please include a message"}), 400

@app.route('/create-user', methods=['POST'])
@auth.login_required
def create_user():
    user_data = request.json
    if 'username' and 'password' in user_data:
        encode_password = base64.b64encode(bytes(user_data['password'], 'utf-8'))
        user_name = user_data['username']
        try:
            user = User(name=user_name, password=encode_password)
            user.validate()
            user.save()
            return ('', 204)
        except ValidationError:
            return jsonify({"status", "Username already taken"}), 400
        pass
    else: 
        return jsonify({"status", "Invalid request"}), 400

@auth.verify_password
def verify_password(username, password):
    user = User.objects().get(name=username)
    if user is not None and check_password_hash(user['password'], password):
        return user



if __name__ == '__main__':
    app.run(host='0.0.0.0')
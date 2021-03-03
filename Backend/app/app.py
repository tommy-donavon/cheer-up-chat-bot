import json
import numpy
import random


from mongoengine.errors import NotUniqueError, ValidationError
from werkzeug.security import generate_password_hash
from flask import Flask, request, jsonify
from bot.bot import Bot
from bot import nltk_utils
from pathlib import Path
from authentication import auth, User


app = Flask(__name__)


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
@auth.login_required
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
def create_user():
    user_data = request.json
    if 'username' and 'password' in user_data:
        encode_password = generate_password_hash(user_data['password'])
        user_name = user_data['username']
        try:
            user = User(name=user_name, password=encode_password)
            user.validate()
            user.save()
            return ('', 204)
        except ValidationError and NotUniqueError:
            return jsonify({"status": "Username already taken"}), 400
    else: 
        return jsonify({"status": "Invalid request"}), 400

@app.route('/login', methods=['POST'])
@auth.login_required
def check_for_user():
    return jsonify({"username": auth.current_user().name}), 200




if __name__ == '__main__':
    app.run(host='0.0.0.0')
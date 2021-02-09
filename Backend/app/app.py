from flask import Flask, request, jsonify, make_response
from bot.bot import Bot
from pathlib import Path
import json
import numpy


app = Flask(__name__)

json_path = Path(__file__).parent / "./bot/data.json"
bot_path = Path(__file__).parent / "./bot/model.tflearn"

with json_path.open() as f:
    data = json.load(f)
    training = numpy.array(data['training'])
    output = numpy.array(data['output'])


model = Bot(training=training, output = output)
model.model.load(bot_path)



@app.route('/', methods=['POST'])
def bot_response():
    raw_data = request.json
    if 'message' in raw_data:
        pass

    else:
        return jsonify({"status":"Bad_Request"}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0')
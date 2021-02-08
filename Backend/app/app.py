from flask import Flask, request, jsonify, make_response
from bot.bot import Bot
import tflearn

app = Flask(__name__)



@app.route('/', methods=['POST'])
def bot_response():
    raw_data = request.json
    if 'message' in raw_data:
        pass

    else:
        return jsonify({"status":"Bad_Request"}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0')
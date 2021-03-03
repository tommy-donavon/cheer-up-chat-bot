from flask import Flask
from flask_restful import Api
from resources.bot_resource import Bot_Resource
from resources.create_user_resource import Create_User
from resources.login_resource import Login

app = Flask(__name__)
api = Api(app)

api.add_resource(Bot_Resource, '/')
api.add_resource(Create_User, '/create-user')
api.add_resource(Login, '/login')

if __name__ == '__main__':
    app.run(host='0.0.0.0')
from mongoengine.errors import NotUniqueError, ValidationError
from werkzeug.security import generate_password_hash
from flask import request
from authentication import User
from flask_restful import Resource



class Create_User(Resource):

    def post(self):
        """
            Creates user and stores them in database with password salted and hashed.
            Successful request gives back as 204 NO CONTENT.
            If request is poorly formatted or username is already taken 400 is returned.
        """
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
                return {"status": "Username already taken"}, 400
        else: 
            return {"status": "Invalid request"}, 400
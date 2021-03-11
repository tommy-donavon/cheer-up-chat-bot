import os
from mongoengine import connect, Document, StringField
from flask_httpauth import HTTPBasicAuth
from mongoengine.errors import DoesNotExist
from werkzeug.security import check_password_hash


USER = os.environ.get("MONGO_USER")
PWD = os.environ.get("MONGO_PASS")
HOST = os.environ.get("MONGO_HOST")
DATABASE = os.environ.get("MONGO_DATABASE")
PORT = os.environ.get("MONGO_PORT")

DB_URI = f'mongodb://{USER}:{PWD}@{HOST}:{PORT}/{DATABASE}?authSource=admin'

db = connect(host=DB_URI)
auth = HTTPBasicAuth()

# model for User collection
class User(Document):
    name = StringField(required=True, unique=True)
    password = StringField(required=True)

@auth.verify_password
def verify_password(username, password):
    """
        Returns the user object if the supplied username and password
        match our records. Returns False otherwise.
    """
    try:
        user = User.objects().get(name=username)
    except DoesNotExist:
        return False
    if check_password_hash(pwhash=user.password, password=password):
        return user




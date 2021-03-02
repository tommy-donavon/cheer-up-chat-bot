from mongoengine import connect, Document, StringField
from flask_httpauth import HTTPBasicAuth
from mongoengine.errors import DoesNotExist
from werkzeug.security import check_password_hash



DB_URI = 'mongodb+srv://User:TWKUDY8qLS2Db0hE@cluster0.a8rpi.mongodb.net/se?retryWrites=true&w=majority'
db = connect(host=DB_URI)
auth = HTTPBasicAuth()

class User(Document):
    name = StringField(required=True, unique=True)
    password = StringField(required=True)

@auth.verify_password
def verify_password(username, password):
    try:
        user = User.objects().get(name=username)
    except DoesNotExist:
        return False
    if check_password_hash(pwhash=user.password, password=password):
        print('checkpoint')
        return user




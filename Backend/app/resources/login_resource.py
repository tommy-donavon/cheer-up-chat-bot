from authentication import auth
from flask_restful import Resource

class Login(Resource):

    @auth.login_required
    def post(self):
        """
            simply checks if provided creds match any records
        """
        return {"username": auth.current_user().name}, 200
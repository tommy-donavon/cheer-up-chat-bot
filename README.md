# Cheer-Up ChatBot

Cheer-Up ChatBot is a school project designed to utilize machine learning basics to analyze user text.
### Authors
- [Tommy Donavon](https://github.com/yhung-mea7)
- [Odin Nakasone](https://github.com/OdinNakasone)
- [Reece Zarkowski](https://github.com/JordanR5a)
- [Justin Spurlock](https://github.com/SpurlockWorkspace)
- [Mason Bradford](https://github.com/masonbrad831)

## Installation

In order to successfully run the program. you must have the following installed:
- [Docker](https://docs.docker.com/get-docker/)
- [Android Studio](https://developer.android.com/studio)
- Please follow this documentation to build your own android emulator
    - [Docs](https://developer.android.com/studio/run/managing-avds)
   - We recommend using the Android 8.0 OS (oreo)
   - It is also recommanded that the emulator be based on an existing smartphone as opposed to a tablet

## Starting the Application
The Cheer-Up ChatBot comes in two parts: A Backend API and a frontend android application.

### Start Backend:
To start the backend service please click the start windows batch file found in the root project directory. If the batch file fails to start the API please access the backend directory through a windows command prompt and run the following command:
- Make sure you do not have any services currently running on port 8080

```bash
docker-compose up --build
```
*Note the initial build process of the backend service will take a while as it needs to install the necessary dependencies and train the bot so please be patient. Subsequent start-ups will be much quicker.

You will know if the service is successfully up if the following log appears in your command window

```bash
Running on http://0.0.0.0:5000/ (Press CTRL+C to quit)
```
### Start Frontend:
Once you have Android Studio installed with a working emulator open up the frontend directory in the IDE and make sure your emulator is selected in the build taskbar. Once this is done simply click on the run option.

*Note the backend service must be started FIRST in order for the application to function properly.


## Usage

With both services started up you can continue through the main screen as a guest or create an account and begin conversing with the bot.

If you would like to access the API through a request service such as [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/) you may do the following:

- Send a POST request to http://localhost:8080/ with a JSON body formatted as follows
```json
{
   "message":"<your message here>"
}
``` 
- Note even though the API will say it's running on port 5000 in the docker container it is being routed through a NGINX load balancer listening on port 8080.
- The API is secured using HTTP Basic if you would like to create an accound you can send a POST request to http://localhost:8080/create-user with a JSON body formatted as follows
```json
{
    "username":"<your username>",
    "password":"<your password>"
}
```

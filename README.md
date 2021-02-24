# Cheer-Up ChatBot

Cheer-Up ChatBot is a school project designed to utilize machine learning basics to analyze user text.

## Installation

In order to successfully run the program. you must have the following installed:
- [Docker](https://docs.docker.com/get-docker/)
- [Android Studio](https://developer.android.com/studio)
- Please follow this documentation to build your own android emulator
    - [Docs](https://developer.android.com/studio/run/managing-avds)
   - We recommend using the Android 8.0 OS (oreo)

## Starting the Application
The Cheer-Up ChatBot comes in two parts: A Backend API and a frontend android application.

### Start Backend:
To start the backend service please click the start windows batch file found in the root project directory. If the batch file fails to start the API please access the backend directory through a windows command prompt and run the following command:

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

With both services started up you can continue through the main screen as a guest (authorization is not implemented at this point in time) and begin conversing with the bot.

If you would like to access the API through a request service such as [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/) you may do the following:

- Send a POST request to http://localhost:8080/ with a JSON body formatted as follows
```json
{
   "message":"<your message here>"
}
``` 
- Note even though the API will say it's running on port 5000 in the docker container it is being routed through a NGINX load balancer listening on port 8080.

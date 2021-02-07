FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y build-essential python3.6-dev python3-pip python3.6-venv
RUN pip3 install --upgrade pip

RUN set -ex && mkdir /app
WORKDIR /app
COPY /app ./app

RUN set -ex && pip3 install -r app/requirements.txt

ENV PYTHONPATH /app
RUN cd app/bot && python3 training.py

CMD ["python3", "./app/app.py"]

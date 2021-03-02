FROM python:3.8-buster

RUN pip3 install --upgrade pip

RUN set -ex && mkdir /app
WORKDIR /app
COPY /app ./app

RUN set -ex && pip3 install -r app/requirements.txt

ENV PYTHONPATH /app
RUN cd app/bot && python3 training.py

CMD ["python3", "./app/app.py"]

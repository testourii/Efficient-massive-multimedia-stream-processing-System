import time
import sys
import cv2
import time
from kafka import KafkaProducer
from kafka.errors import KafkaError

producer = KafkaProducer(bootstrap_servers='localhost:9092')
topic = 'my-topic'


def emit_video(path_to_video):
    print('start')

    video = cv2.VideoCapture(path_to_video)
    i=-1
    while video.isOpened():
        i+=1
        success, frame = video.read()
        if not success:
            break
        
        	# png might be too large to emit
        data = cv2.imencode('.jpeg', frame)[1].tobytes()
            #print(type(data))
        future = producer.send(topic, data)
        try:
           future.get(timeout=10)
        except KafkaError as e:
           print(e)
           break
        print(".", end="", flush=True)
        time.sleep(0.1)
        if i==400: break
       
emit_video(0)

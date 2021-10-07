from kafka import KafkaConsumer
import json
from datetime import datetime
consumer = KafkaConsumer('topic-out', bootstrap_servers='localhost:9092')


def kafkastream():
    i=0
    now=datetime.now()
    for message in consumer:
    	#for x in message[5]:
        
        #print('alert !!')
        h=message.value.decode("utf-8")
        s=h
        s = s.replace('\t','')
        s = s.replace('\n','')
        s = s.replace(',}','}')
        s = s.replace('{','')
        s = s.replace('}]','')
        s = s.replace('[{','')
        #data = json.loads(s)
        #print(s)
        
        #print(type(s))
        arr = s.split('},')
        for aa in arr:
        	aa=aa.replace('[','')
        	aa=aa.replace(']','')
        	aa=aa.replace(' ','')
        	hh=aa.split(',')
        	if len(hh[0])>1: 
        		
        		print('alert N°'+str(i)+' !! : '+hh[0])
        		print("\n")
        		break;
        i+=1
        later = datetime.now()
        if i%10==0:
        	print(later-now)
        	now=datetime.now()
        


kafkastream()

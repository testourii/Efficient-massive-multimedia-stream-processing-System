Cancel changes
# MAFC: Multimedia Adaptable Flow Controller in Big Data systems [![Tests status](https://img.shields.io/badge/Kafka-%20-lightgrey)](https://github.com/optiopay/kafka/actions?query=workflow%3AKafka) ![enter image description here](https://img.shields.io/badge/Flink%20-%20-orange) ![enter image description here](https://img.shields.io/badge/Python-%20-green) ![enter image description here](https://img.shields.io/badge/YOLO-%20-yellow)

This project is realized within the context of the end-of-studies project of a research master in IoT and data processing at Tunisia Polytechnic School with SERCOM laboratory under the supervision of **Mme Takoua ABDELLATIF**  and **Mr Aymen YAHYAOUI**.

## Introduction 
The mind blowing Statistics show that in the last two years alone, 90% of data has been generated globally.
and according to cisco, video streaming and downloading is expected to account for 82% of global internet traffic. - 15 times more than in 2017
Despite this huge amount of data, manual analysis can only distinguish 12% of the valuable data, which is not enough to meet current needs.
All these figures require more efficient extraction of value from data, which places higher demands on real-time multimedia processing for big data systems.
## Problematic 
In the IoT context, traditional video surveillance systems use multimedia processing engines to analyze incoming streams and send extracted events to interested parties.
While these  systems provide real-time multimedia processing, Scalability remains a nightmare characterized by the volume and velocity of this data because they cannot keep pace with the addition of new cameras at exponentially higher bandwidth and processing rates.
The problem is that the data bandwidth can exceed the processing time, which can lead to data loss or delay in real-time processing. In addition, intensive data processing can impact on  power consumption and even damage system hardware.
[![i1.png](https://i.postimg.cc/QCfDHxvY/i1.png)](https://postimg.cc/4HHq0Zfp)
## MAFC architecture
In this context, We propose MAFC, a multimedia adaptable flow controller for Big Data systems that ensures adaptability with system scalability without data loss and with performance optimization according to the application context.

As Shown here,For the data collection, the proposed architecture is able to ingest video data from either unbounded video streaming data or stored multimedia sequences (1).

Once the multimedia data has been acquired, it will decomposed into a sequence of individual multimedia objects that will be fed one by one to the flow controller (2).

Based on the calculated control policies by the policy adapter (8), the flow controller choose which frame will be published to the message broker on the concerned topic and which is not (3).

Then, the real-time data processing engine will extract the specific events from the in coming frames in real-time and publish them to the concerned topics (6) to consume them by the media stream controller in order to automatically update the control policies and by third-party services to notify concerned parties (7).
[![implementation.jpg](https://i.postimg.cc/3R6C7gxt/implementation.jpg)](https://postimg.cc/S26zfY89)
## Installation
I am running this project on Ubuntu 20.04 , and i will cover installation for that.

####  Installing the Default JRE/JDK

Java and the JVM (Java’s virtual machine) are required for  Apache Kafka and Flink 


first update the package index:
```sh
sudo apt update
```

Next, check if Java is already installed:
```sh
java -version
```
If Java is not currently installed, you’ll see the following output:

```
Output
Command 'java' not found, but can be installed with:

sudo apt install openjdk-11-jre-headless  # version 11.0.11+9-0ubuntu2~20.04, or
sudo apt install default-jre              # version 2:1.11-72
sudo apt install openjdk-13-jre-headless  # version 13.0.7+5-0ubuntu1~20.04
sudo apt install openjdk-16-jre-headless  # version 16.0.1+9-1~20.04
sudo apt install openjdk-8-jre-headless   # version 8u292-b10-0ubuntu1~20.04
```

Execute the following command to install the default Java Runtime Environment (JRE), which will install the JRE from OpenJDK 11:
```
sudo apt install default-jre
 ```
The JRE will allow you to run almost all Java software.
Verify the installation with:

```
java -version
```
You’ll see output similar to the following:

```
Output
openjdk version "11.0.11" 2021-04-20
OpenJDK Runtime Environment (build 11.0.11+9-Ubuntu-0ubuntu2.20.04)
OpenJDK 64-Bit Server VM (build 11.0.11+9-Ubuntu-0ubuntu2.20.04, mixed mode, sharing))
```
You may need the Java Development Kit (JDK) in addition to the JRE in order to compile and run some specific Java-based software. To install the JDK, execute the following command:
```
apt-get install openjdk-8-jdk
``````

####  Installing Kafka
>Getting Kafka up and running can be a bit tricky, so I’d recommend a Google search to match your setup.

Now before we can start Kafka itself, we will need to install that ZooKeeper we talked about earlier.

```
sudo apt-get install zookeeperd
```
ZooKeeper will kick of automatically as a daemon set to port 2181. Let’s make sure it’s running with:
```
netstat -ant | grep :2181
```
The output we want to see is :
```
tcp6       0      0 :::2181           :::*         LISTEN
```
With that we’re ready for Kafka.

We can wget the download from the Apache site with
```
wget http://apache.claz.org/kafka/1.0.1/kafka_2.11-1.0.1.tgz
```
Make a directory for the extracted file.
```
sudo mkdir /opt/Kafka
```
Now extract the Kafka file to our newly minted directory.
```
sudo tar -xvf kafka_2.11-1.0.1.tgz -C /opt/Kafka/
```
Ok, let’s run Kafka!
```
cd /opt/Kafka/kafka_2.11-1.0.1/  
sudo bin/kafka-server-start.sh config/server.properties
```
Test that everything is up and running, open a new terminal and type,
```
sudo bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testing
```
You should see,


```
Created topic "testing".
```

### Installing IntelliJ IDE
To install IntelliJ IDE **Community Edition** through snap packages, open the terminal and type the command mentioned below:
```
sudo  snap  install  intellij-idea-community  --classic
```
## kafka on our project
To get our Kafka clients up and running, we’ll need the Kafka-Python project.

And, while we’re at it, we’ll also need OpenCV for video rendering, as well as Flask for our “distributed” Consumer.
```
pip install kafka-python opencv-contrib-python Flask
```
The Kafka Server we set up in the last section is bound to port 9092. We’ll use this value when setting up our two Kafka clients.
### I-Producer
The first of our Kafka clients will be the message Producer. Here it will be responsible for converting video to a stream of JPEG images.

 the Producer defaults by streaming video directly from the web cam — assuming you have one. If pulling from a video file is more your style (I recommend 15MB and smaller), the Producer accepts a file name as a command-line argument.
### II-Consumer
 **Video Srtream consumer**
To read our newly published stream, we’ll need a Consumer that accesses our Kafka topic. Since our message streamer was intended for a distributed system, we’ll keep our project in that spirit and launch our Consumer as a Flask service.
 **Detected object consumer (from Flink)**
 This consumer aims to subscribe to extracted events from  Flink and published in a separate topic.
As I mentioned before, Kafka gives a lot of the stream-access discretion to the Consumer which help us in implementing our MAFC system

 If you’re interested, the [Kafka-Python Documentation](http://kafka-python.readthedocs.io/en/master/usage.html) provides an in-depth look at everything that’s available.



## Run Project
we’ll need to start up Kafka, the two Consumers, and finally the Producer — each in their own terminal.
As demonstrated previously, we start Kafka with a simple:
```
cd /opt/Kafka/kafka_2.11-1.0.1/  
sudo bin/kafka-server-start.sh config/server.properties
```
In a new terminal, we’ll start up Consumer-video-stream file  with:
```
cd /Kafka  
python consumer-video-stream.py
```
If everything is working, your terminal should read
```
* Running on [http://0.0.0.0:5000/](http://0.0.0.0:5000/) (Press CTRL+C to quit)
```
In the browser, go to  [http://0.0.0.0:5000/video](http://0.0.0.0:5000/video)  . You won’t see anything here yet, but keep it open because it’s about to come to life.

Also, you need to run the second consumer to get the extracted events from image using flink:
```
python consumer-flink.py
```

For the Producer, it’s more of the same. First, open a new terminal:
```
python producer.py
```
 Here, we’ll be streaming from the web cam, so no additional arguments are needed. but the second consumer will not work as we didn't run the flink project yet.
 
 ### Running Flink project
 Finally, we need to import the flink project with IntelliJ IDE and then, install dependencies and run StreamingJob.java under Flink-part/src/main/java/jar/ .


## References

If you’re interested,  in-depth look references  are linked below.

|   references |
|  ------ |
|  [Flink](https://nightlies.apache.org/flink/flink-docs-master/) |
| [Kafka](https://kafka.apache.org/documentation/) |
|[YOLO](https://pjreddie.com/darknet/yolo/) |
| [Kafka-Python Documentation](http://kafka-python.readthedocs.io/en/master/usage.html)  | [plugins/onedrive/README.md] |


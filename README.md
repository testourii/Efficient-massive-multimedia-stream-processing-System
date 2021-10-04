
# Multimedia Adaptable Flow Controller in Big Data systems

It's a project in the context of the end of year project of a research master in IoT and data processing at Tunisia Polytechnic School

## Introduction 
The mind blowing Statistics show that in the last two years alone, 90% of data has been generated globally.
and according to cisco, video streaming and downloading is expected to account for 82% of global internet traffic. - 15 times more than in 2017
Despite this huge amount of data, manual analysis can only distinguish 12% of the valuable data, which is not enough to meet current needs.
All these figures require more efficient extraction of value from data, which places higher demands on real-time multimedia processing for big data systems.
## Problematic 
In the IoT context, traditional video surveillance systems use multimedia processing engines to analyze incoming streams and send extracted events to interested parties.
While these  systems provide real-time multimedia processing, Scalability remains a nightmare characterized by the volume and velocity of this data because they cannot keep pace with the addition of new cameras at exponentially higher bandwidth and processing rates.
The problem is that the data bandwidth can exceed the processing time, which can lead to data loss or delay in real-time processing. In addition, intensive data processing can impact on  power consumption and even damage system hardware.
![Problematic](https://ibb.co/BC3XDsq)
## MAFC architecture
In this context, We propose MAFC, a multimedia adaptable flow controller for Big Data systems that ensures adaptability with system scalability without data loss and with performance optimization according to the application context.

As Shown here,For the data collection, the proposed architecture is able to ingest video data from either unbounded video streaming data or stored multimedia sequences (1).

Once the multimedia data has been acquired, it will decomposed into a sequence of individual multimedia objects that will be fed one by one to the flow controller (2).

Based on the calculated control policies by the policy adapter (8), the flow controller choose which frame will be published to the message broker on the concerned topic and which is not (3).

Then, the real-time data processing engine will extract the specific events from the in coming frames in real-time and publish them to the concerned topics (6) to consume them by the media stream controller in order to automatically update the control policies and by third-party services to notify concerned parties (7).
![enter image description here](https://ibb.co/s6j5fFy)

## Installation

### References

Dillinger is currently extended with the following plugins. Instructions on how to use them in your own application are linked below.

|  | reference |
| ------ | ------ |
| Flink | [plugins/dropbox/README.md][PlDb] |
| Kafka | [plugins/github/README.md][PlGh] |
| YOLO | [plugins/googledrive/README.md][PlGd] |
| Python | [plugins/onedrive/README.md][PlOd] |
| Medium | [plugins/medium/README.md][PlMe] |
| Google Analytics | [plugins/googleanalytics/README.md][PlGa] |
# Welcome to StackEdit!


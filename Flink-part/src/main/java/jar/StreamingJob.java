/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jar;
import com.github.chen0040.objdetect.ObjectDetector;
import com.github.chen0040.objdetect.models.DetectedObj;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
//************************
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
//************************

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
//************************
import java.util.List;
import java.util.Properties;

public class StreamingJob  {


	static final ObjectDetector detector = new ObjectDetector();
	public static void main(String[] args) throws Exception {
		// create execution environment


		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();



		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("zookeeper.connect", "localhost:2181");
		properties.setProperty("group.id", "test");
		DataStream<byte[]> stream = env
				.addSource(new FlinkKafkaConsumer010<>("my-topic", new AbstractDeserializationSchema<byte[]>() {
					@Override
					public byte[] deserialize(byte[] bytes) throws IOException {
						return bytes;
					}
				}, properties));

		detector.loadModel();
		//
		FlinkKafkaProducer010<String> myProducer = new FlinkKafkaProducer010<String>(
				"topic-out",                  // target topic
				new SimpleStringSchema(),    // serialization schema
				properties);
		DataStreamSink<String> map = stream.map(new MapFunction<byte[], String>() {
			//private static final long serialVersionUID = -6867736771747690202L;
			@Override
			public String map(byte[] value) throws Exception {
				InputStream is = new ByteArrayInputStream(value);
				BufferedImage newBi = ImageIO.read(is);


				List<DetectedObj> result = detector.detectObjects(newBi);

				return result.toString();
			}
		}).addSink(myProducer);

		 // fault-tolerance
		//map.addSink(myProducer);

		env.execute();
	}

}
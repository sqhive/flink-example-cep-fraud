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

package london.sqhive.flink.examples.cep;

import london.sqhive.flink.examples.cep.conditions.DebitCondition;
import london.sqhive.flink.examples.cep.conditions.DebitConditionEqualValue;
import london.sqhive.flink.examples.cep.functions.EventMap;
import london.sqhive.flink.examples.cep.functions.FraudPatternSelectFunction;
import london.sqhive.flink.examples.cep.models.Event;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;


public class Application {

	public static void main(String[] args) throws Exception {

		// the host and the port to connect to
		final String hostname;
		final int port;
		try {
			final ParameterTool params = ParameterTool.fromArgs(args);
			hostname = params.has("hostname") ? params.get("hostname") : "localhost";
			port = params.getInt("port");
		} catch (Exception e) {
			System.err.println("No port specified. Please run 'FraudExample " +
					"--hostname <hostname> --port <port>', where hostname (localhost by default) " +
					"and port is the address of the text server");
			return;
		}

		// get the execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// get input data by connecting to the socket
		DataStream<String> text = env.socketTextStream(hostname, port, "\n");

		// parse the data
		DataStream<Event> events = text.map(new EventMap());

		Pattern<Event, ?> pattern = Pattern
			.<Event>begin("start")
			.where(new DebitCondition())
			.next("middle")
			.where(new DebitConditionEqualValue())
			.within(Time.seconds(10));

		PatternStream<Event> patternStream = CEP.pattern(events, pattern);

		DataStream<String> result = patternStream.select(new FraudPatternSelectFunction());

		// print the results with a single thread, rather than in parallel
		events.print().setParallelism(1);
		result.print().setParallelism(1);

		env.execute("Fraud Example");
	}
}

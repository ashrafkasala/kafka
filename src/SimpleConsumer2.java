import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleConsumer2 {


    public static void main(String[] args) throws Exception {

//        String topicName = "test-topic-partition3";
        String topicName = "test";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-consumer-group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "5000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);

//        Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(topicName));

//        //to read from specific offset
//        TopicPartition topicPartition = new TopicPartition(topicName, 0);
//        List<TopicPartition> topics = Arrays.asList(topicPartition);
//        consumer.assign(topics);
////        consumer.seekToEnd(topics);
//        long current = consumer.position(topicPartition);
//        consumer.seek(topicPartition, 1);

        //print the topic name
        System.out.println("Subscribed to topic " + topicName);
        int i = 0;
        long start = System.currentTimeMillis();
        System.out.println(new Date() + "\n");

        Date startTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        int intervalTimeInMin = 1;
        int intervalTimeInSec = 3;
        int intervalTimeInMilliSec = 100;
        long offsetFirst = 0;
        boolean firstTime = true;
        MYSQLConnect mysqlConnect = new MYSQLConnect();

        String topic = "";
        long partition = 0;

        //find specific offset

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(500);
            //fetch first record offset
            for (ConsumerRecord<String, String> record : records) {

                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s,partition = %s\n",
                        record.offset(), record.key(), record.value(),record.partition());
                offsetFirst = record.offset();
                topic = record.topic();
                partition = record.partition();
                System.out.println("records count " + records.count());
                break;
            }
            Date currentTime = new Date();
//            Date targetTime = DateUtils.addMinutes(startTime, intervalTimeInMin);	addMilliseconds
//            Date targetTime = DateUtils.addMilliseconds(startTime, intervalTimeInMilliSec);
            Date targetTime = DateUtils.addSeconds(startTime, intervalTimeInSec);
            System.out.println("current time " + dateFormat.format(currentTime));
            System.out.println("target time " + dateFormat.format(targetTime));
            if (firstTime) {
                mysqlConnect.insert(offsetFirst, startTime, topic, partition);
                System.out.println("offset at " + dateFormat.format(startTime) + " is " + offsetFirst);
                firstTime = false;
            }
            if (currentTime.after(targetTime)) {
                startTime = targetTime;
                //save to db the offset
                mysqlConnect.insert( offsetFirst, targetTime, topic, partition);
                System.out.println("offset at " + dateFormat.format(targetTime) + " is " + offsetFirst);
            }

        }
    }
}
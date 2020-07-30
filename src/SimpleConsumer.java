import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Arrays;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.*;

public class SimpleConsumer {

    boolean printOffset(long offset, long start, long interval, long lastOffset) {
        long step = 1;
        long end = System.currentTimeMillis();
        long diff = end - start;
        if (diff > step * interval && offset != lastOffset) {
            lastOffset = offset;
            System.out.println("offset with duration of 5ms is " + lastOffset + " at " + step * interval);
            System.out.println("Difference is : " + diff);
            step++;

        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        String topicName = "test";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer
                <String, String>(props);

        //Kafka Consumer subscribes list of topics here.
//        consumer.subscribe(Arrays.asList(topicName));

        //to read from specific offset
        TopicPartition topicPartition = new TopicPartition(topicName, 0);
        List<TopicPartition> topics = Arrays.asList(topicPartition);
        consumer.assign(topics);
//        consumer.seekToEnd(topics);
        long current = consumer.position(topicPartition);
        consumer.seek(topicPartition, 1100034);

        //print the topic name
        System.out.println("Subscribed to topic " + topicName);
        int i = 0;
        long start = System.currentTimeMillis();
        System.out.println(new Date() + "\n");


        long step = 1;
        long offset = 0;
        long lastOffset = -1;
        long interval = 5;//ms

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC Time is: " + dateFormat.format(date));
        int addMinuteTime = 1;

        Date targetTime = DateUtils.addMinutes(date, addMinuteTime); //add minute
        System.out.println("UTC Time afetr adding 5 mins is: " + dateFormat.format(targetTime));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1);
            long end = System.currentTimeMillis();
            long diff = end - start;
//            if (diff > step * interval && offset != lastOffset) {
//                lastOffset = offset;
//                System.out.println("offset with duration of 5ms is " + lastOffset + " at " + step * interval);
//                System.out.println("Difference is : " + diff);
//                step++;
//            }
            if (diff > step * interval && offset != lastOffset) {
                lastOffset = offset;
                System.out.println("offset with duration of 5ms is " + offset + " at " + interval * (diff / interval));
                System.out.println("Difference is : " + diff + "with records count "+records.count());
                step++;
            }
            for (ConsumerRecord<String, String> record : records) {

                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s\n",
                        record.offset(), record.key(), record.value());
                offset = record.offset();
            }

        }
    }
}
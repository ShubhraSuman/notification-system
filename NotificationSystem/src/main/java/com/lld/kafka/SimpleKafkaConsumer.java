package com.lld.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleKafkaConsumer {

    public static void consume(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email-service-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");// ✅ only consume new messages

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        System.out.println("📩 EmailConsumer started, listening to email-topic...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
            for (ConsumerRecord<String, String> record : records) {
                try {
                    System.out.println("📧 Sending EMAIL to user " +
                            record.key() + ": " + record.value() + " from topic: " + record.topic());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

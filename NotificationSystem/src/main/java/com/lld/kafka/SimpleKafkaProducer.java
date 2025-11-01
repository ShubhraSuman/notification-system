package com.lld.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleKafkaProducer {
    public static void sendNotification(String key, String value, String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker address
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Step 2: Create the producer
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

            // Step 4: Send the message
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("✅ Sent message to topic " + metadata.topic() +
                            " partition " + metadata.partition() +
                            " offset " + metadata.offset());
                } else {
                    exception.printStackTrace();
                }
            });

            // Step 5: Flush and close producer
            producer.flush();
        }
    }
}

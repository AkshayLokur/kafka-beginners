package org.example.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoKeys {
    private static final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String bootstrapServers = "localhost:9092";

        // Create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 5; i++) {
            // Produce 5 messages
            produceMessage(producer, i);
        }

        // Flush data and close
        producer.flush();
        producer.close();
    }

    private static void produceMessage(Producer<String, String> producer, Integer i) throws ExecutionException, InterruptedException {
        // Create producer record
        String topicName = "second_topic";
        String key = "id_" + Integer.toString(i);
        String value = "Hello world, message number: " + Integer.toString(i);

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);
        logger.info("Key: " + key);

        // Send data
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                // Executes every time a record has been successfully sent or an exception is thrown
                if (e == null) {
                    // record successfully sent
                    logger.info("---- Received new metadata.\n" +
                            "Topic: " + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp() + "\n" +
                            "----"
                    );
                } else {
                    logger.error("Error occurred while producing", e);
                }

            }
        }).get(); // .get() makes send "synchronous". Not to be done in production!
    }
}

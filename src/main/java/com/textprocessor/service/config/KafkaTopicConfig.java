package com.textprocessor.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic wordsProcessedTopic(
        @Value("${app.kafka-topic}") String topic,
        @Value("${app.kafka-topic-partitions:1}") int partitions,
        @Value("${app.kafka-topic-replication-factor:1}") short replicationFactor) {
        return new NewTopic(topic, partitions, replicationFactor);
    }
}

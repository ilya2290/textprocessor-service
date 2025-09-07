package com.textprocessor.service.service.external.kafka;

import com.textprocessor.service.responses.ParagraphResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, ParagraphResponse> kafkaTemplate;
    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, ParagraphResponse> kafkaTemplate,
                                @Value("${app.kafka-topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendParagraphResult(ParagraphResponse result) {
        String key = String.valueOf(result.hashCode());
        kafkaTemplate.send(topic, key, result);

        log.info("Sent TextResult to Kafka: key={}, mostFrequentWord={}", key, result.mostFrequentWord());
    }
}

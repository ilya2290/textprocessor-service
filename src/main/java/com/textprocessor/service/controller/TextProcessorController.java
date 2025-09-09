package com.textprocessor.service.controller;

import com.textprocessor.service.constants.Endpoints;
import com.textprocessor.service.responses.ParagraphResponse;
import com.textprocessor.service.service.external.kafka.KafkaProducerService;
import com.textprocessor.service.service.impl.TextProcessorServiceImpl;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Endpoints.BETVICTOR_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class TextProcessorController {

    private final TextProcessorServiceImpl processingService;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/text")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ParagraphResponse> getText(@RequestParam @Positive int p) {
        ParagraphResponse response = processingService.processParagraphs(p);
        this.kafkaProducerService.sendParagraphResult(response);

        return ResponseEntity.ok().body(response);
    }

}

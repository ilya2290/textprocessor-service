package com.textprocessor.service.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HipsumService {

    private final WebClient webClient;
    private static final String DEFAULT_PARAGRAPH = "Default hipster paragraph text cause of exception.";

    public String getParagraph() {
        try {
            return webClient.get()
                .uri("/api/?type=hipster-centric&paras=1")
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> Mono.error(new RuntimeException("HipSum API error: " + response.statusCode()))
                )
                .bodyToMono(String.class)
                .block();
        } catch (Exception e) {
            return DEFAULT_PARAGRAPH;
        }
    }
}

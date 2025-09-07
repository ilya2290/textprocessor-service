package com.textprocessor.service.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class HipsumService {

    private final WebClient webClient;

    public String getParagraph() {
        return webClient.get()
            .uri("/api/?type=hipster-centric&paras=1")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}

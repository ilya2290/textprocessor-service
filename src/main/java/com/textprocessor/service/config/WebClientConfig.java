package com.textprocessor.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.hipsum.base-url}")
    private String baseUrl;

    @Bean
    public WebClient hipsumWebClient() {
        int maxInMemorySize4MB = 4 * 1024 * 1024;

        return WebClient.builder()
            .baseUrl(baseUrl)
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(c -> c.defaultCodecs().maxInMemorySize(maxInMemorySize4MB))
                .build())
            .build();
    }
}

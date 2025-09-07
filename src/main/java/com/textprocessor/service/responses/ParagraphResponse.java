package com.textprocessor.service.responses;

import lombok.Builder;

@Builder
public record ParagraphResponse(String mostFrequentWord,
                                long avgParagraphSize,
                                long avgParagraphProcessingTime,
                                long totalProcessingTime) {
}

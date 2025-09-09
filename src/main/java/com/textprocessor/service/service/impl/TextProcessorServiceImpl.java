package com.textprocessor.service.service.impl;

import com.textprocessor.service.responses.ParagraphResponse;
import com.textprocessor.service.service.external.HipsumService;
import com.textprocessor.service.service.TextProcessorService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TextProcessorServiceImpl implements TextProcessorService {

    private final HipsumService hipsumService;

    @Override
    public ParagraphResponse processParagraphs(int paragraphCount) {
        List<String> paragraphs = new ArrayList<>();
        List<Long> processingTimes = new ArrayList<>();
        Instant startTotal = Instant.now();

        for (int i = 0; i < paragraphCount; i++) {
            Instant start = Instant.now();
            String paragraph = hipsumService.getParagraph();
            Instant end = Instant.now();

            paragraphs.add(paragraph);
            processingTimes.add(Duration.between(start, end).toMillis());
        }
        
        Map<String, Long> wordFrequency = collectAllWords(paragraphs);
        String mostFrequentWord = mostFrequencyWord(wordFrequency);
        long avgParagraphSize = avgParagraphSize(paragraphs);
        long avgProcessingTime = avgParagraphAnalysisTime(processingTimes);
        long totalProcessingTime = totalProcessingTime(startTotal);

        return ParagraphResponse.builder()
            .avgParagraphProcessingTime(avgProcessingTime)
            .avgParagraphSize(avgParagraphSize)
            .mostFrequentWord(mostFrequentWord)
            .totalProcessingTime(totalProcessingTime)
            .build();
    }

    private static Map<String, Long> collectAllWords(List<String> paragraphs) {
        Map<String, Long> wordFrequency = new HashMap<>();
        paragraphs.forEach(paragraph -> Arrays.stream(paragraph.replaceAll("[^a-zA-Z ]", Strings.EMPTY).toLowerCase().split("\\s+"))
            .forEach(word -> wordFrequency.put(word, wordFrequency.getOrDefault(word, 0L) + 1)));
        return wordFrequency;
    }

    private static long avgParagraphAnalysisTime(List<Long> processingTimes) {
        double avgAnalyticsTime = processingTimes.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0);

        return Math.round(avgAnalyticsTime);

    }

    private static long avgParagraphSize(List<String> paragraphs) {
        double avgSize = paragraphs.stream()
            .mapToInt(String::length)
            .average()
            .orElse(0);

        return Math.round(avgSize);
    }

    private static String mostFrequencyWord(Map<String, Long> wordFrequency) {
        long max = wordFrequency.values().stream()
            .max(Long::compareTo)
            .orElse(0L);

        return wordFrequency.entrySet().stream()
            .filter(e -> e.getValue() == max)
            .map(Map.Entry::getKey).min(Comparator.comparingInt(String::length).reversed()
                .thenComparing(Comparator.naturalOrder()))
            .orElse(Strings.EMPTY);
    }

    private static long totalProcessingTime(Instant startTotal) {
        return Duration.between(startTotal, Instant.now()).toMillis();
    }

}

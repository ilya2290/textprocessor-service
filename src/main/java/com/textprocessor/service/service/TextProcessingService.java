package com.textprocessor.service.service;

import com.textprocessor.service.responses.ParagraphResponse;

public interface TextProcessingService {

    ParagraphResponse processParagraphs(int paragraphCount);
}

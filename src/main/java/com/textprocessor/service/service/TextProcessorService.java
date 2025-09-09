package com.textprocessor.service.service;

import com.textprocessor.service.responses.ParagraphResponse;

public interface TextProcessorService {

    ParagraphResponse processParagraphs(int paragraphCount);
}

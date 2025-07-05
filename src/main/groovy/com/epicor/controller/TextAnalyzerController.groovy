package com.epicor.controller

import com.epicor.model.AnalysisResult
import com.epicor.service.TextAnalyzerService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping('/api')
@Slf4j
class TextAnalyzerController {

    @Autowired TextAnalyzerService textAnalyzerService

    @GetMapping('/text')
    ResponseEntity<AnalysisResult> analyzeText() {
        AnalysisResult result = textAnalyzerService.analyzeFromClassPath()
        logToConsole(result)
        return ResponseEntity.ok(result)
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<AnalysisResult> analyzeUploadedFile(@RequestParam("file") MultipartFile file) {
        try {
            AnalysisResult result = textAnalyzerService.analyzeTextFromInput(file.getInputStream());
            logToConsole(result)
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void logToConsole(AnalysisResult result) {
        log.info('Analysis complete: Total words: {}, Top words: {}, Unique words: {}',
                result.totalWordCount, result.topWords.size(), result.uniqueWords.size())
    }
}
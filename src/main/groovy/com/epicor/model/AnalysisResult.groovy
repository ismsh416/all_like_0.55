package com.epicor.model

import lombok.Data

@Data
class AnalysisResult {
    int totalWordCount
    List<WordResult> topWords
    List<String> uniqueWords
    double processingTime
}

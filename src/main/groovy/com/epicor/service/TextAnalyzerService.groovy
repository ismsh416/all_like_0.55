package com.epicor.service

import com.epicor.model.AnalysisResult
import com.epicor.model.WordResult
import com.epicor.util.WordsUtil
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import java.time.Duration
import java.time.Instant
import java.util.stream.Collectors

@Service
@Slf4j
class TextAnalyzerService {

    private static final String REGEX = '[^a-zA-Z]+'
    AnalysisResult analyzeFromClassPath() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream('moby.txt')
        if (inputStream == null) {
            log.error('Input stream is null, file not found')
            throw new FileNotFoundException('File not found: moby.txt')
        }
        return analyzeTextFromInput(inputStream)
    }

    AnalysisResult analyzeTextFromInput(InputStream inputStream) {
        try {
            log.info('Starting text analysis')
            Instant start = Instant.now()
            Map<String, Integer> wordCount = new HashMap<>()
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
            String line

            while((line = bufferedReader.readLine()) != null){
                String[] words = line.toLowerCase().split(REGEX)
                for (String raw : words) {
                    if (raw.isEmpty()) continue
                    String word = normalize(raw)

                    if (!WordsUtil.STOP_WORDS.contains(word) && word.length() > 1) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1)
                    }
                }

            }
            bufferedReader.close()
            int totalWords = wordCount.values().stream().mapToInt(Integer::intValue).sum()
            List<WordResult> topWords = wordCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .map(entry -> new WordResult(word: entry.key, count: entry.value))
                    .collect(Collectors.toList())

            List<String> uniqueWords = wordCount.keySet().stream()
                    .sorted()
                    .limit(50)
                    .collect(Collectors.toList())

            Instant end = Instant.now()
            double durationSeconds = Math.round(Duration.between(start, end).toMillis() / 1000.0 * 100.0) / 100.0

            return new AnalysisResult(
                    totalWordCount: totalWords,
                    topWords: topWords,
                    uniqueWords: uniqueWords,
                    processingTime: durationSeconds
            )

        } catch (Exception e) {
            log.error('error:{}', e.printStackTrace())
            throw new Exception('Exception occurred while reading')
        }

    }

    private String normalize(String word) {
        if (word.endsWith("'s")) {
            return word.substring(0, word.length() - 2)
        }
        return word
    }
}

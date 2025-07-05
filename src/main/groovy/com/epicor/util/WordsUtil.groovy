package com.epicor.util

import groovy.transform.CompileStatic

@CompileStatic
class WordsUtil {
    static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "a", "an", "the", "in", "on", "at", "by", "for", "with", "without", "to",
            "from", "and", "or", "but", "so", "because", "if", "while", "as", "of",
            "he", "she", "it", "they", "we", "you", "i", "is", "was", "are", "were",
            "be", "been", "being", "has", "have", "had", "do", "does", "did", "that",
            "this", "those", "these", "not", "his", "her", "its", "our", "their", "my",
            "me", "him", "them", "which", "who", "whom", "what", "where", "when", "why",
            "how", "will", "shall", "would", "should", "can", "could", "may", "might",
            "must", "ought", "also", "there"
    ));
}

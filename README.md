# Text Analysis API (Java 17 + Spring Boot + Groovy)

This Spring Boot application analyzes a `.txt` file and provides word statistics such as:
- Total word count
- Top 5 most frequent words (excluding stop words)
- Top 50 alphabetically sorted unique words

---

## 🔧 Features

- ✅ Analyze bundled `moby.txt` file (GET endpoint)
- ✅ Accept `.txt` file upload and analyze it (POST endpoint)
- ✅ Filters out common stop words (e.g., "is", "the", "and")
- ✅ Ignores punctuation, numbers, case, and `'s` suffix
- ✅ Calculates and logs processing time in seconds (2 decimal places)

---

## 📂 Endpoints

### 1. **Analyze default file**
Analyze the `moby.txt` file located in `src/main/resources`.

- **Method**: `GET`
- **URL**: `/api/text`
- **Response**: JSON with word stats

#### ✅ Example Response
```json
{
  "totalWordCount": 21054,
  "topWords": [
    { "word": "whale", "count": 812 },
    { "word": "sea", "count": 342 },
    { "word": "like", "count": 330 },
    { "word": "upon", "count": 285 },
    { "word": "ship", "count": 276 }
  ],
  "uniqueWords": [
    "aaron", "abandon", "abandoned", ..., "zoned", "zoology"
  ],
  "processingTimeInSeconds": 1.24
}

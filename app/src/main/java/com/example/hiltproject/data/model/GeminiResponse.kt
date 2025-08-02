package com.example.hiltproject.data.model

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: GeneratedContent?
)

data class GeneratedContent(
    val parts: List<GeneratedPart>?
)

data class GeneratedPart(
    val text: String?
)

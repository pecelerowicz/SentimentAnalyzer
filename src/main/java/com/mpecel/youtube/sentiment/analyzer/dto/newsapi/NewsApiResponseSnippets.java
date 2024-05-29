package com.mpecel.youtube.sentiment.analyzer.dto.newsapi;

public record NewsApiResponseSnippets() {
    public record Pair(
            String query,
            int number
    ) {
    }
}

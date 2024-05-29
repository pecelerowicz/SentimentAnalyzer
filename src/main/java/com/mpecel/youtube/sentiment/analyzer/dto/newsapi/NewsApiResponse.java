package com.mpecel.youtube.sentiment.analyzer.dto.newsapi;

import java.util.List;

public record NewsApiResponse(
        String status,
        int totalResults,
        List<Article> articles
) {
    public record Article(
            Source source,
            String author,
            String title,
            String description,
            String url,
            String urlToImage,
            String publishedAt,
            String content
    ) {
    }

    public record Source(
            String id,
            String name
    ) {
    }
}

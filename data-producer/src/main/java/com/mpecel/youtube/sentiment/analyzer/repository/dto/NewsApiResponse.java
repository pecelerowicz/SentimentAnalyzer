package com.mpecel.youtube.sentiment.analyzer.repository.dto;

import org.springframework.data.annotation.Id;

import java.util.List;

public record NewsApiResponse(
        @Id
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

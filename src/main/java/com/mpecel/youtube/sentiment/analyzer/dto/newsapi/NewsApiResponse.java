package com.mpecel.youtube.sentiment.analyzer.dto.newsapi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "newsApiResponses")
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

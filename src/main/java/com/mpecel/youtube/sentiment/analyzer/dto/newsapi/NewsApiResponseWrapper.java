package com.mpecel.youtube.sentiment.analyzer.dto.newsapi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "newsApiResponses")
public record NewsApiResponseWrapper(
        @Id String id,
        String query,
        Date queryDate,
        NewsApiResponse newsApiResponse
) {
}

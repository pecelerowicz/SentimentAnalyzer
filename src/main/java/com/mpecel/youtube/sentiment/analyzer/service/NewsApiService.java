package com.mpecel.youtube.sentiment.analyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiClientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NewsApiService {

    private final NewsApiClientRepository newsApiClientRepository;
    private final ObjectMapper objectMapper;

    @Value("${crypto.queries}")
    private String cryptoQueries;

    public NewsApiResponse getFreshResponse(String query) {
        return newsApiClientRepository.getNews(query);
    }

    public void updateSavedResponses(String query) {
        NewsApiResponse newsApiResponse = newsApiClientRepository.getNews(query);
    }

    public NewsApiService(NewsApiClientRepository newsApiClientRepository, ObjectMapper objectMapper) {
        this.newsApiClientRepository = newsApiClientRepository;
        this.objectMapper = objectMapper;
    }
}

package com.mpecel.youtube.sentiment.analyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseSnippets;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsApiService {

    private final NewsApiRepository newsApiRepository;
    private final ObjectMapper objectMapper;

    @Value("${crypto.queries}")
    private String cryptoQueries;

    public NewsApiResponse getNews(String query) {
        return getNewsApiResponse(newsApiRepository.getNews(query));
    }

    public NewsApiResponseSnippets getNewsSnippets() {
        List<String> listOfQueries = List.of(cryptoQueries.split(","));
        listOfQueries.forEach(q -> {
            NewsApiResponse newsApiResponse = getNewsApiResponse(newsApiRepository.getNews(q));
            System.out.println(q);
            System.out.println(newsApiResponse.totalResults());
            System.out.println("---");
        });


        return null;
    }

    public NewsApiService(NewsApiRepository newsApiRepository, ObjectMapper objectMapper) {
        this.newsApiRepository = newsApiRepository;
        this.objectMapper = objectMapper;
    }

    private NewsApiResponse getNewsApiResponse(String rawDataNews) {
        try {
            return objectMapper.readValue(rawDataNews, NewsApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO improve
        }
    }
}

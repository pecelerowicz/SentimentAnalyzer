package com.mpecel.youtube.sentiment.analyzer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;

@Repository
public class NewsApiClientRepository {

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    @Value("${api.newsapi.url.base}")
    private String apiNewsapiUrlBase;

    @Value("${api.newsapi.key}")
    private String apiNewsapiKey;

    @Value("${api.newsapi.domains}")
    private String apiNewsapiDomains;

    public NewsApiResponse getNews(String query) {
        NewsApiResponse newsApiResponseFirstPage = getPageOfNews(query, 1);
        if(newsApiResponseFirstPage.totalResults() <= 100) {
            return newsApiResponseFirstPage;
        } else {
            LinkedList<NewsApiResponse.Article> articles = new LinkedList<>(newsApiResponseFirstPage.articles());
            int numberOfPages = (newsApiResponseFirstPage.totalResults() / 100) + 1;
            for(int page=2; page<=numberOfPages; page++) {
                articles.addAll(getPageOfNews(query, page).articles());
            }
            return new NewsApiResponse(newsApiResponseFirstPage.status(),
                    newsApiResponseFirstPage.totalResults(), articles);
        }

    }

    private NewsApiResponse getPageOfNews(String query, int page) {
        try {
            URIBuilder uriBuilder = new URIBuilder(apiNewsapiUrlBase);
            uriBuilder.addParameter("apiKey", apiNewsapiKey);
            uriBuilder.addParameter("q", query);
            uriBuilder.addParameter("page", String.valueOf(page));
            uriBuilder.addParameter("domains", apiNewsapiDomains);
            URI uri = uriBuilder.build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            String body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            try {
                NewsApiResponse newsApiResponse = objectMapper.readValue(body, NewsApiResponse.class);
                return newsApiResponse;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e); //TODO improve
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewsApiClientRepository(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }
}

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
        try {
            URIBuilder uriBuilder = new URIBuilder(apiNewsapiUrlBase);
            uriBuilder.addParameter("apiKey", apiNewsapiKey);
            uriBuilder.addParameter("q", query);
            uriBuilder.addParameter("domains", apiNewsapiDomains);
            URI uri = uriBuilder.build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            String body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            try {
                return objectMapper.readValue(body, NewsApiResponse.class);
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

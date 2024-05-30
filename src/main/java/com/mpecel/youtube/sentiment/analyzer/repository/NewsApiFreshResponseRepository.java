package com.mpecel.youtube.sentiment.analyzer.repository;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class NewsApiFreshResponseRepository {

    private final HttpClient httpClient;

    @Value("${api.newsapi.url.base}")
    private String apiNewsapiUrlBase;

    @Value("${api.newsapi.key}")
    private String apiNewsapiKey;

    @Value("${api.newsapi.domains}")
    private String apiNewsapiDomains;

    public String getNews(String query) {
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

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewsApiFreshResponseRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}

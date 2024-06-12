package com.mpecel.youtube.sentiment.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.repository.dto.RedditResponse;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RedditClientRepository {

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    private static final String BASE_URL = "https://oauth.reddit.com/r/";
    private static final String USER_AGENT = "java:your-app-name:v1.0 (by /u/Sea_Height_9280)";
    private static final String AUTH_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IlNIQTI1NjpzS3dsMnlsV0VtMjVmcXhwTU40cWY4MXE2OWFFdWFyMnpLMUdhVGxjdWNZIiwidHlwIjoiSldUIn0.eyJzdWIiOiJsb2lkIiwiZXhwIjoxNzE3NzY1NzQ5LjIzNjgxNSwiaWF0IjoxNzE3Njc5MzQ5LjIzNjgxNSwianRpIjoiWVltUGU1YjNBVVlrVDFsbFFWblA3RTJNMzF4NC1RIiwiY2lkIjoiODlBU3V5b1FTdDBmY1Q5bUdJNnk4ZyIsImxpZCI6InQyXzExZWRsbzNreDEiLCJsY2EiOjE3MTcwMTg0ODc1MjMsInNjcCI6ImVKeUtWdEpTaWdVRUFBRF9fd056QVNjIiwiZmxvIjo2fQ.hyM71QTmOdpTIlZwAfKMSKnAYQtIQC913wKuVhnV4kHKxUwEJMkrKp8b8DldEMpFXxQvhKhSLRpcIsAKiNqT89qmzcd-eFYGkMoTB2o2ShMm-ZDgy1VHE2rhQ3vsS8cSDuFQVr3GxQ_ATd8t_AEWnMGPMBQKgko14ZXnf3FAJZPCSMKaUH_LYUqZZHNvJbjt41P7TmzREjhxXNn-xaW5lJsk_SFRLAfCPAcWO-TgHFZHWZmX0Y4N7FeS_-aUUzude_7PIjMauhtC2tfeF4vqUJ5-pVkNrFaCbAPs515f0Lo87731e6ICQNmoNbRbXURQYpyQ9iqDLWxAjzvcxN8ybg";

    public RedditClientRepository(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public RedditResponse getLatestPosts(String subreddit) {
        String url = BASE_URL + subreddit + "/new";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", USER_AGENT)
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .GET()
                .build();

        try {
            HttpResponse<String> responses = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (responses.statusCode() == 200) {
                return parseRedditResponse(responses.body());
            } else {
                System.err.println("Error: " + responses.statusCode() + " - " + responses.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private RedditResponse parseRedditResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, RedditResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

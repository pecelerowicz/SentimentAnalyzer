package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.mongo.NewsApiResponseWrapper;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/na")
public class NewsApiController {

    private final NewsApiService newsApiService;

    @GetMapping("/news/fresh/{query}")
    public NewsApiResponse getFreshResponse(@PathVariable String query) {
        return newsApiService.getFreshResponse(query);
    }

    @GetMapping("/news/saved/{query}")
    public NewsApiResponse getSavedResponse(@PathVariable String query) {
        return newsApiService.getSavedResponse(query);
    }

    @GetMapping("/news/all/{query}")
    public List<NewsApiResponseWrapper> getSavedResponses(@PathVariable String query) {
        return newsApiService.getSavedResponses(query);
    }

    @PostMapping("/news/all/{query}")
    public void updateSavedResponses(@PathVariable String query) {
        newsApiService.updateSavedResponses(query);
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

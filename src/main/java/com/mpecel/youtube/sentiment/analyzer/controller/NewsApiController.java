package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/na")
public class NewsApiController {

    private final NewsApiService newsApiService;

    // todo to chyba powinno zniknąć
    @GetMapping("/news/{query}/fresh")
    public NewsApiResponse getFreshResponse(@PathVariable String query) {
        return newsApiService.getFreshResponse(query);
    }

    @PostMapping("/news/all/{query}/")
    public void updateSavedResponses(@PathVariable String query) {
        newsApiService.updateSavedResponses(query);
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

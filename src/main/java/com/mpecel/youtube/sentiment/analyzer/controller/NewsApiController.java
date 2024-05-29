package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseSnippets;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/na")
public class NewsApiController {

    private final NewsApiService newsApiService;

    @GetMapping("/news/{query}")
    public NewsApiResponse getNews(@PathVariable String query) {
        return newsApiService.getNews(query);
    }

    @GetMapping("/news/snippets")
    public NewsApiResponseSnippets getNewsSnippets() {
        return newsApiService.getNewsSnippets();
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

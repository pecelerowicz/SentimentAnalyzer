package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.dto.ArticlesResponse;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/na/analytics")
public class NewsApiController {

    private final NewsApiService newsApiService;

    @GetMapping("/{query}")
    ArticlesResponse getAllArticles(@PathVariable String query,
                                    @RequestParam(value = "published-from-date", required = false) String publishedFromDate,
                                    @RequestParam(value = "published-to-date", required = false) String publishedToDate) {
        return newsApiService.getAllArticles(query, publishedFromDate, publishedToDate);
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

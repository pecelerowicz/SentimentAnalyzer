package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.dto.ArticlesResponse;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/{query}/{country-of-zone}")
    ArticlesResponse getAllArticlesByZoneDate(@PathVariable("query") String query,
                                              @PathVariable("country-of-zone") String countryOfZone,
                                              @RequestParam(value = "published-from-date", required = false) LocalDateTime publishedFromDate,
                                              @RequestParam(value = "published-to-date", required = false) LocalDateTime publishedToDate) {
        return newsApiService.getAllArticlesByZoneDate(query, countryOfZone, publishedFromDate, publishedToDate);
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

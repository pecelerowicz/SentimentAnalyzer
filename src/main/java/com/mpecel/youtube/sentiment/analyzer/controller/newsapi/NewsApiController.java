package com.mpecel.youtube.sentiment.analyzer.controller.newsapi;

import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiInfo;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseSnippet;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseWrapper;
import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/na")
public class NewsApiController {

    private final NewsApiService newsApiService;

    // todo to chyba powinno zniknąć
    @GetMapping("/news/{query}/fresh")
    public NewsApiResponse getFreshResponse(@PathVariable String query) {
        return newsApiService.getFreshResponse(query);
    }

    // TODO powinno zniknąć
    @GetMapping("/news/{query}/saved/last")
    public NewsApiResponse getLastSavedResponse(@PathVariable String query) {
        return newsApiService.getLastSavedResponse(query);
    }

    // TODO powinno zniknąć
    @GetMapping("/news/{query}/saved/all")
    public List<NewsApiResponseWrapper> getSavedResponses(@PathVariable String query) {
        return newsApiService.getSavedResponses(query);
    }

    // TODO powinno zniknąć
    @GetMapping("/news/{query}/saved/snippets")
    public List<NewsApiResponseSnippet> getSavedResponsesSnippets(@PathVariable String query) {
        return newsApiService.getSavedResponsesSnippets(query);
    }

    // todo to powinno zniknąć i powinno być robione cyklicznie samo.
    @PostMapping("/news/{query}")
    public void updateSavedResponses(@PathVariable String query) {
        newsApiService.updateSavedResponses(query);
    }

    @GetMapping("/news/all")
    public NewsApiInfo getInfo() {
        return newsApiService.getInfo();
    }

    @DeleteMapping("/news/all")
    public void clearAll() {
        newsApiService.deleteSavedResponses();
    }

    public NewsApiController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }
}

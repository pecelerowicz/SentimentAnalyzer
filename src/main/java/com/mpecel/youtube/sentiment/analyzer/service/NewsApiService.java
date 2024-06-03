package com.mpecel.youtube.sentiment.analyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.model.Article;
import com.mpecel.youtube.sentiment.analyzer.model.Snapshot;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiClientArticleRepository;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiClientRepository;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiClientSnapshotJpaRepository;
import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsApiService {

    private final NewsApiClientRepository newsApiClientRepository;
    private final NewsApiClientSnapshotJpaRepository newsApiClientSnapshotJpaRepository;
    private final NewsApiClientArticleRepository newsApiClientArticleRepository;
    private final ObjectMapper objectMapper;

    @Value("${crypto.queries}")
    private String cryptoQueries;

    public NewsApiResponse getFreshResponse(String query) {
        return newsApiClientRepository.getNews(query);
    }

    public void createSnapshot(String query) {
        NewsApiResponse newsApiResponse = newsApiClientRepository.getNews(query);
        List<Article> articles = newsApiResponse.articles().stream().map(Article::new).toList();

        Snapshot snapshot = new Snapshot();
        snapshot.setQuery(query);
        snapshot.setTotalResults(newsApiResponse.totalResults());
        snapshot.setQueryDate(LocalDateTime.now());

        List<Article> existingOrNewArticles = new ArrayList<>();
        for (Article article : articles) {
            Optional<Article> existingArticle = newsApiClientArticleRepository
                    .findByTitleAndDescriptionAndPublishedAt(article.getTitle(), article.getDescription(), article.getPublishedAt());
            if (existingArticle.isPresent()) {
                existingOrNewArticles.add(existingArticle.get());
            } else {
                existingOrNewArticles.add(article);
            }
        }

        snapshot.setArticles(existingOrNewArticles);
        newsApiClientSnapshotJpaRepository.save(snapshot);
    }

    public NewsApiService(NewsApiClientRepository newsApiClientRepository, NewsApiClientSnapshotJpaRepository newsApiClientSnapshotJpaRepository, NewsApiClientArticleRepository newsApiClientArticleRepository, ObjectMapper objectMapper) {
        this.newsApiClientRepository = newsApiClientRepository;
        this.newsApiClientSnapshotJpaRepository = newsApiClientSnapshotJpaRepository;
        this.newsApiClientArticleRepository = newsApiClientArticleRepository;
        this.objectMapper = objectMapper;
    }
}

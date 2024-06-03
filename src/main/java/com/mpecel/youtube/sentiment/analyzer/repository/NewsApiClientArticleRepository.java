package com.mpecel.youtube.sentiment.analyzer.repository;

import com.mpecel.youtube.sentiment.analyzer.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsApiClientArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByTitleAndDescriptionAndPublishedAt(String title, String description, String publishedAt);
}

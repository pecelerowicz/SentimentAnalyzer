package com.mpecel.youtube.sentiment.analyzer.repository;

import com.mpecel.youtube.sentiment.analyzer.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

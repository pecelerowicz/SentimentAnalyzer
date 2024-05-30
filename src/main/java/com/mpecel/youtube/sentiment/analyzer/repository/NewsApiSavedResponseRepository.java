package com.mpecel.youtube.sentiment.analyzer.repository;

import com.mpecel.youtube.sentiment.analyzer.mongo.NewsApiResponseWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NewsApiSavedResponseRepository extends MongoRepository<NewsApiResponseWrapper, String> {
    List<NewsApiResponseWrapper> findByQuery(String query);
}
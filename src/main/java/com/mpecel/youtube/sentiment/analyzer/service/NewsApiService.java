package com.mpecel.youtube.sentiment.analyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiSavedResponseRepository;
import com.mpecel.youtube.sentiment.analyzer.mongo.NewsApiResponseWrapper;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiFreshResponseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsApiService {

    private final NewsApiFreshResponseRepository newsApiFreshResponseRepository;
    private final NewsApiSavedResponseRepository newsApiSavedResponseRepository;
    private final ObjectMapper objectMapper;

    @Value("${crypto.queries}")
    private String cryptoQueries;

    public NewsApiResponse getFreshResponse(String query) {
        return getNewsApiResponse(newsApiFreshResponseRepository.getNews(query));
    }

    // TODO napisać to ładniej
    public NewsApiResponse getSavedResponse(String query) {
        List<NewsApiResponseWrapper> wrappers = newsApiSavedResponseRepository.findByQuery(query);
        List<NewsApiResponseWrapper> listOfWrappers = wrappers.stream().filter(w -> w.query().equals(query)).toList();
        if(!listOfWrappers.isEmpty()) {
            NewsApiResponseWrapper wrapper = listOfWrappers.getLast();
            NewsApiResponse newsApiResponse = wrapper.newsApiResponse();
            return newsApiResponse;
        }
        return null;
    }

    public List<NewsApiResponseWrapper> getSavedResponses(String query) {
        List<NewsApiResponseWrapper> list = newsApiSavedResponseRepository.findByQuery(query).stream().toList();
        System.out.println(list.size());
        return list;
//        return newsApiSavedResponseRepository.findByQuery(query).stream().filter(w -> w.query().equals(query)).toList();
    }

    public void updateSavedResponses(String query) {
        NewsApiResponse freshResponse = getFreshResponse(query);
        NewsApiResponseWrapper wrapper = new NewsApiResponseWrapper(null, query, new Date(), freshResponse);
        newsApiSavedResponseRepository.save(wrapper);
    }

    public NewsApiService(NewsApiFreshResponseRepository newsApiFreshResponseRepository, NewsApiSavedResponseRepository newsApiSavedResponseRepository, ObjectMapper objectMapper) {
        this.newsApiFreshResponseRepository = newsApiFreshResponseRepository;
        this.newsApiSavedResponseRepository = newsApiSavedResponseRepository;
        this.objectMapper = objectMapper;
    }

    private NewsApiResponse getNewsApiResponse(String rawDataNews) {
        try {
            return objectMapper.readValue(rawDataNews, NewsApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO improve
        }
    }
}

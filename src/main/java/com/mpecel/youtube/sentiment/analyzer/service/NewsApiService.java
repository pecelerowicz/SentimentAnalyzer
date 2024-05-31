package com.mpecel.youtube.sentiment.analyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiInfo;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseSnippet;
import com.mpecel.youtube.sentiment.analyzer.repository.NewsApiSavedResponseRepository;
import com.mpecel.youtube.sentiment.analyzer.dto.newsapi.NewsApiResponseWrapper;
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
    public NewsApiResponse getLastSavedResponse(String query) {
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
        return newsApiSavedResponseRepository.findByQuery(query).stream().filter(w -> w.query().equals(query)).toList();
    }

    // todo implement
    public List<NewsApiResponseSnippet> getSavedResponsesSnippets(String query) {
        throw new RuntimeException("Not implemented yet");
    }

    public void updateSavedResponses(String query) {
        NewsApiResponse freshResponse = getFreshResponse(query);
        NewsApiResponseWrapper wrapper = new NewsApiResponseWrapper(null, query, new Date(), freshResponse);
        newsApiSavedResponseRepository.save(wrapper);
    }

    // todo implement
    public NewsApiInfo getInfo() {
        throw new RuntimeException("Not implemented yet");
    }

    public void deleteSavedResponses() {
        throw new RuntimeException("Uncomment to delete");
        //        newsApiSavedResponseRepository.deleteAll();
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

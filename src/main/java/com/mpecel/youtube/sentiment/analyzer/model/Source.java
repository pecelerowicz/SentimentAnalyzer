//package com.mpecel.youtube.sentiment.analyzer.model;
//
//import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
//import jakarta.persistence.*;
//import java.util.List;
//
//@Entity
//public class Source {
//
//    @Id
//    private String id;
//
//    private String name;
//
//    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Article> articles;
//
//    public Source() {
//    }
//
//    Source(NewsApiResponse.Source source) {
//        this.id = source.id();
//        this.name = source.name();
//    }
//
//    // Getters and Setters
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<Article> getArticles() {
//        return articles;
//    }
//
//    public void setArticles(List<Article> articles) {
//        this.articles = articles;
//    }
//}

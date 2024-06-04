package com.mpecel.youtube.sentiment.analyzer.model;

import com.mpecel.youtube.sentiment.analyzer.repository.dto.NewsApiResponse;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(mappedBy = "articles")
    private List<Snapshot> snapshots;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "source_id", nullable = false)
//    private Source source;

    @Column(length = 10000)
    private String title;
    @Column(length = 10000)
    private String author;
    @Column(length = 10000)
    private String description;
    @Column(length = 10000)
    private String url;
    @Column(length = 10000)
    private String urlToImage;
    @Column(length = 10000)
    private String publishedAt;
    @Column(length = 10000)
    private String content;
    @Column(length = 10000)
    private String source;
    @Column(length = 10000)
    private String sourceId;

    public Article() {
    }

    public Article(NewsApiResponse.Article article) {
//        this.source = new Source(article.source());
        this.title = article.title();
        this.author = article.author();
        this.description = article.description();
        this.url = article.url();
        this.urlToImage = article.urlToImage();
        this.publishedAt = article.publishedAt();
        this.content = article.content();
        this.source = article.source().name();
        this.sourceId = article.source().id();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}

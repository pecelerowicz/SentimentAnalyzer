package com.mpecel.youtube.sentiment.analyzer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Article implements Comparable<Article> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(mappedBy = "articles")
    private List<Snapshot> snapshots;

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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    @Override
    public int compareTo(Article other) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime thisDate = LocalDateTime.parse(this.publishedAt, formatter);
        LocalDateTime otherDate = LocalDateTime.parse(other.publishedAt, formatter);
        int dateComparison = thisDate.compareTo(otherDate);
        if (dateComparison != 0) {
            return dateComparison;
        } else {
            return Long.compare(this.id, other.id);
        }
    }
}

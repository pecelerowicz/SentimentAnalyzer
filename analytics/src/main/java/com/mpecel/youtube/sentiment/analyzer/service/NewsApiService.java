package com.mpecel.youtube.sentiment.analyzer.service;

import com.mpecel.youtube.sentiment.analyzer.dto.ArticleResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.ArticlesResponse;
import com.mpecel.youtube.sentiment.analyzer.model.Article;
import com.mpecel.youtube.sentiment.analyzer.model.Snapshot;
import com.mpecel.youtube.sentiment.analyzer.repository.SnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsApiService {

    private final SnapshotRepository snapshotRepository;

    public NewsApiService(SnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    @Transactional
    public ArticlesResponse getAllArticles(String query, String publishedFrom, String publishedTo) {
        List<ArticleResponse> articleResponseList = new LinkedList<>();
        List<Snapshot> querySnapshots = snapshotRepository.findAll().stream().filter(s -> s.getQuery().equals(query)).sorted().toList();

        Set<Article> queryArticles = new HashSet<>();
        querySnapshots.forEach(s -> {
            HashSet<Article> articles = new HashSet<>(s.getArticles());
            queryArticles.addAll(articles);
        });

        List<LocalDateTime> querySnapshotDates = querySnapshots
                .stream()
                .sorted()
                .map(Snapshot::getQueryDate)
                .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime fromDate = publishedFrom != null ? ZonedDateTime.parse(publishedFrom, formatter) : null;
        ZonedDateTime toDate = publishedTo != null ? ZonedDateTime.parse(publishedTo, formatter) : null;
        List<Article> queryArticlesSortedFiltered = queryArticles
                .stream()
                .filter(article -> {
                    ZonedDateTime publishedAt = ZonedDateTime.parse(article.getPublishedAt(), formatter);
                    boolean afterFromDate = fromDate == null || !publishedAt.isBefore(fromDate);
                    boolean beforeToDate = toDate == null || !publishedAt.isAfter(toDate);
                    return afterFromDate && beforeToDate;
                })
                .sorted().toList();

        queryArticlesSortedFiltered.forEach(a -> {
            List<LocalDateTime> articleQuerySnapshotDates = a.getSnapshots()
                    .stream()
                    .sorted()
                    .filter(s -> s.getQuery().equals(query))
                    .map(Snapshot::getQueryDate)
                    .toList();

            Set<String> allQueriesOfArticle = a.getSnapshots()
                    .stream()
                    .map(Snapshot::getQuery)
                    .collect(Collectors.toSet());

            long id = a.getId();
            String title = a.getTitle();
            String url = a.getUrl();
            ZonedDateTime publishedAt = ZonedDateTime.parse(a.getPublishedAt(), formatter);
            articleResponseList.add(new ArticleResponse(id, title, url, publishedAt, querySnapshotDates,
                    articleQuerySnapshotDates, allQueriesOfArticle));
        });

        return new ArticlesResponse(articleResponseList, articleResponseList.size());
    }

    private Set<Article> union(Set<Article> set1, Set<Article> set2) {
        Set<Article> unionSet = new HashSet<>(set1);
        unionSet.addAll(set2);
        return unionSet;
    }

    private Set<Article> intersection(Set<Article> set1, Set<Article> set2) {
        Set<Article> intersectionSet = new HashSet<>(set1);
        intersectionSet.retainAll(set2);
        return intersectionSet;
    }

    private Set<Article> difference(Set<Article> set1, Set<Article> set2) {
        Set<Article> differenceSet = new HashSet<>(set1);
        differenceSet.removeAll(set2);
        return differenceSet;
    }
}

package com.mpecel.youtube.sentiment.analyzer.service;

import com.mpecel.youtube.sentiment.analyzer.dto.ArticleResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.ArticlesResponse;
import com.mpecel.youtube.sentiment.analyzer.model.Article;
import com.mpecel.youtube.sentiment.analyzer.model.Snapshot;
import com.mpecel.youtube.sentiment.analyzer.repository.ArticleRepository;
import com.mpecel.youtube.sentiment.analyzer.repository.SnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsApiService {

    private final SnapshotRepository snapshotRepository;
    private final ArticleRepository articleRepository;

    public NewsApiService(SnapshotRepository snapshotRepository, ArticleRepository articleRepository) {
        this.snapshotRepository = snapshotRepository;
        this.articleRepository = articleRepository;
    }

    List<ArticleResponse> articleResponseList = new LinkedList<>();

    @Transactional
    public ArticlesResponse getAllArticles(String query) {
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

        List<Article> queryArticlesSorted = queryArticles.stream().sorted().toList();

        queryArticlesSorted.forEach(a -> {
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
            LocalDateTime publishedAt = ZonedDateTime.parse(a.getPublishedAt()).toLocalDateTime();
            articleResponseList.add(new ArticleResponse(id, title, url, publishedAt, querySnapshotDates,
                    articleQuerySnapshotDates, allQueriesOfArticle));
        });

        return new ArticlesResponse(articleResponseList);
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

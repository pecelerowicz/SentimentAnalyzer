package com.mpecel.youtube.sentiment.analyzer.service;

import com.mpecel.youtube.sentiment.analyzer.dto.ArticleResponse;
import com.mpecel.youtube.sentiment.analyzer.dto.ArticlesResponse;
import com.mpecel.youtube.sentiment.analyzer.model.Article;
import com.mpecel.youtube.sentiment.analyzer.model.Snapshot;
import com.mpecel.youtube.sentiment.analyzer.repository.SnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @Transactional
    public ArticlesResponse getAllArticlesByZoneDate(String query, String countryOfZone,
                                                     LocalDateTime publishedFromDate, LocalDateTime publishedToDate) {

        ZoneId zoneId = getZoneIdForCountry(countryOfZone);

        String publishedFromZoned = null;
        String publishedToZoned = null;

        if (publishedFromDate != null) {
            publishedFromZoned = publishedFromDate.atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC")).toString();
        }

        if (publishedToDate != null) {
            publishedToZoned = publishedToDate.atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC")).toString();
        }

        return getAllArticles(query, publishedFromZoned, publishedToZoned);
    }

    private ZoneId getZoneIdForCountry(String countryOfZone) {
        Map<String, String> countryZoneMap = new HashMap<>();
        countryZoneMap.put("USA_Eastern", "America/New_York");
        countryZoneMap.put("USA_Central", "America/Chicago");
        countryZoneMap.put("USA_Mountain", "America/Denver");
        countryZoneMap.put("USA_Pacific", "America/Los_Angeles");
        countryZoneMap.put("UK", "Europe/London");
        countryZoneMap.put("Poland", "Europe/Warsaw");
        countryZoneMap.put("Zulu", "UTC");
        countryZoneMap.put("UTC", "UTC");

        String zoneId = countryZoneMap.get(countryOfZone);
        if (zoneId == null) {
            throw new RuntimeException("Country not found: " + countryOfZone);
        }
        return ZoneId.of(zoneId);
    }
}

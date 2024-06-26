package com.mpecel.youtube.sentiment.analyzer.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public record ArticleResponse(long id,
                              String title,
                              String url,
                              ZonedDateTime publishedAt,
                              List<LocalDateTime> querySnapshotDates,
                              List<LocalDateTime> articleQuerySnapshotDates,
                              Set<String> allQueriesOfArticle) {
}

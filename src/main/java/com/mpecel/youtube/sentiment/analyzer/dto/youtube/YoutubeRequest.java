package com.mpecel.youtube.sentiment.analyzer.dto.youtube;

public record YoutubeRequest(String query,
                             String publishedAfter,
                             String publishedBefore,
                             String regionCode) {
}

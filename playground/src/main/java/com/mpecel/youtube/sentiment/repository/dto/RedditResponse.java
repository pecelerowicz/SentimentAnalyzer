package com.mpecel.youtube.sentiment.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RedditResponse(
        Data data
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            List<Child> children
    ) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Child(
            PostData data
    ) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PostData(
            String selftext,
            String title,
            String subreddit,
            String author,
            double upvote_ratio,
            int ups,
            int score,
            int num_comments,
            double created_utc
    ) {}
}

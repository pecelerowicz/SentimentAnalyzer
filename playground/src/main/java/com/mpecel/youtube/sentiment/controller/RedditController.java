package com.mpecel.youtube.sentiment.controller;

import com.mpecel.youtube.sentiment.service.RedditService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reddit")
public class RedditController {

    private final RedditService redditService;

    @PostMapping("/{subreddit}")
    public void createSnapshot(@PathVariable String subreddit) {
        redditService.createSnapshot(subreddit);
    }

    public RedditController(RedditService redditService) {
        this.redditService = redditService;
    }
}

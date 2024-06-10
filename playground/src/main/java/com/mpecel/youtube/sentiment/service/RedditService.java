package com.mpecel.youtube.sentiment.service;

import com.mpecel.youtube.sentiment.repository.RedditClientRepository;
import com.mpecel.youtube.sentiment.repository.dto.RedditResponse;
import org.springframework.stereotype.Service;

@Service
public class RedditService {

    private final RedditClientRepository redditClientRepository;

    public void createSnapshot(String subreddit) {
        RedditResponse redditResponse = this.redditClientRepository.getLatestPosts(subreddit);
        System.out.println(redditResponse.data().children().get(0));
    }

    public RedditService(RedditClientRepository redditClientRepository) {
        this.redditClientRepository = redditClientRepository;
    }
}

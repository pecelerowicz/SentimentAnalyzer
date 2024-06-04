package com.mpecel.youtube.sentiment.analyzer.scheduled;

import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateSnapshotTask {

    private final NewsApiService newsApiService;

    public CreateSnapshotTask(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    @Scheduled(fixedRate = 10000)
    public void createNewSnapshot() {
        System.out.println("updating:");
        System.out.println(LocalDateTime.now());
        newsApiService.createSnapshot("bitcoin");
        System.out.println("bitcoin");
        newsApiService.createSnapshot("ethereum");
        System.out.println("ethereum");
        newsApiService.createSnapshot("solana");
        System.out.println("solana");
        newsApiService.createSnapshot("dogecoin");
        System.out.println("dogecoin");
        newsApiService.createSnapshot("cardano");
        System.out.println("cardano");
        newsApiService.createSnapshot("chainlink");
        System.out.println("chainlink");
        System.out.println("--- updated ---");
    }

}

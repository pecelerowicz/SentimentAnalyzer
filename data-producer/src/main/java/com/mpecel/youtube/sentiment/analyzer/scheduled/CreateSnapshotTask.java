package com.mpecel.youtube.sentiment.analyzer.scheduled;

import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class CreateSnapshotTask {

    private final NewsApiService newsApiService;

    @Value("${crypto.queries}")
    private String cryptoQueries;

    public CreateSnapshotTask(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    @Scheduled(fixedRate = 3600000)
    public void createNewSnapshot() {
        System.out.println("updating:");
        System.out.println(LocalDateTime.now());
        Arrays.stream(cryptoQueries.split(",")).forEach(q -> {
            System.out.println(q);
            newsApiService.createSnapshot(q);
        });
        System.out.println("--- updated ---");
    }

}

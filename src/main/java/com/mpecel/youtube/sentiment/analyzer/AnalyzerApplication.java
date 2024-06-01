package com.mpecel.youtube.sentiment.analyzer;

import com.mpecel.youtube.sentiment.analyzer.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyzerApplication implements CommandLineRunner {

	@Autowired
	private NewsApiService newsApiService;

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		newsApiService.updateSavedResponses("bitcoin");
	}
}

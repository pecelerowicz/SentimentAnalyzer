package com.mpecel.youtube.sentiment.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class AnalyticsApplication {

	private final DataSource dataSource;

	public AnalyticsApplication(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}

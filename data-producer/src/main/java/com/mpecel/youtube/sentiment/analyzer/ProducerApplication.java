package com.mpecel.youtube.sentiment.analyzer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication implements CommandLineRunner {

	private final DataSource dataSource;

	public ProducerApplication(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		try (Connection conn = dataSource.getConnection();
//			 Statement stmt = conn.createStatement()) {
//			// Wipe out existing data
//			stmt.execute("DELETE FROM snapshot_article");
//			stmt.execute("DELETE FROM article");
//			stmt.execute("DELETE FROM snapshot");
//			stmt.execute("DELETE FROM source");
//
//			// Insert sample data into the source table
//			stmt.execute("INSERT INTO source (id, name) VALUES ('source1', 'Source One'), ('source2', 'Source Two')");
//
//			// Insert sample data into the article table without specifying id
//			stmt.execute("INSERT INTO article (title, author, description, url, url_to_image, published_at, content, source_id) VALUES " +
//					"('Article 1', 'Author 1', 'Description 1', 'http://example.com/1', 'http://example.com/image1', '2024-01-01', 'Content 1', 'source1')," +
//					"('Article 2', 'Author 2', 'Description 2', 'http://example.com/2', 'http://example.com/image2', '2024-01-02', 'Content 2', 'source2')");
//
//			// Insert sample data into the snapshot table
//			stmt.execute("INSERT INTO snapshot (query, query_date) VALUES " +
//					"('Query 1', '2024-01-01 10:00:00'), ('Query 2', '2024-01-02 11:00:00')");
//
//			// Retrieve auto-generated ids for articles
//			try (var rs = stmt.executeQuery("SELECT id FROM article WHERE title = 'Article 1'")) {
//				if (rs.next()) {
//					long article1Id = rs.getLong("id");
//					stmt.execute("INSERT INTO snapshot_article (snapshot_id, article_id) VALUES (1, " + article1Id + "), (2, " + article1Id + ")");
//				}
//			}
//			try (var rs = stmt.executeQuery("SELECT id FROM article WHERE title = 'Article 2'")) {
//				if (rs.next()) {
//					long article2Id = rs.getLong("id");
//					stmt.execute("INSERT INTO snapshot_article (snapshot_id, article_id) VALUES (1, " + article2Id + ")");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}

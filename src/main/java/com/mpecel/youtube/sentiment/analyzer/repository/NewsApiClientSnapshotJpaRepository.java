package com.mpecel.youtube.sentiment.analyzer.repository;

import com.mpecel.youtube.sentiment.analyzer.model.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsApiClientSnapshotJpaRepository extends JpaRepository<Snapshot, Long> {
}

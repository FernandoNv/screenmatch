package com.example.screenmatch.repository;

import com.example.screenmatch.model.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVSeriesRepository extends JpaRepository<TVSeries, Long> {
}

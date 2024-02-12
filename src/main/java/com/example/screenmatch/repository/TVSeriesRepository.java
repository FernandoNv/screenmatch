package com.example.screenmatch.repository;

import com.example.screenmatch.model.Category;
import com.example.screenmatch.model.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TVSeriesRepository extends JpaRepository<TVSeries, Long> {

    Optional<TVSeries> findByTitleContainingIgnoreCase(String tvSeriesTitle);

    List<TVSeries> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);

    List<TVSeries> findTop5ByOrderByRatingDesc();

    List<TVSeries> findByCategory(Category category);

    List<TVSeries> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqualOrderByRatingDesc(Integer maxSeasons, Double minRating);
}

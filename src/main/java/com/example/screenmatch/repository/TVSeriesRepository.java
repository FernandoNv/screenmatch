package com.example.screenmatch.repository;

import com.example.screenmatch.model.Category;
import com.example.screenmatch.model.Episode;
import com.example.screenmatch.model.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TVSeriesRepository extends JpaRepository<TVSeries, Long> {

    Optional<TVSeries> findByTitleContainingIgnoreCase(String tvSeriesTitle);

    List<TVSeries> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);

    List<TVSeries> findTop5ByOrderByRatingDesc();

    List<TVSeries> findByCategory(Category category);

    List<TVSeries> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqualOrderByRatingDesc(int maxSeasons, double minRating);


//    @Query(nativeQuery = true, name = """
//    select id, title from tvseries where total_seasons <= :maxSeasons and rating >= :minRating
//    """)
    @Query("SELECT s FROM TVSeries s WHERE s.totalSeasons <= :maxSeasons AND s.rating >= :minRating")
    List<TVSeries>tvSeriesBySeasonAndRating(int maxSeasons, double minRating);

    @Query("SELECT e FROM TVSeries s JOIN s.episodeList e WHERE e.title ILIKE %:titleEpisode%")
    List<Episode> getEpisodesBySubstringTitle(String titleEpisode);

    @Query("SELECT e FROM TVSeries s JOIN s.episodeList e WHERE s = :tvSeries ORDER BY e.rating DESC LIMIT 5")
    List<Episode> topEpisodesPerTVSeries(TVSeries tvSeries);

    @Query("SELECT e FROM TVSeries s JOIN s.episodeList e WHERE s = :tvSeries AND YEAR(e.releasedDate) >= :year")
    List<Episode> getTVSeriesEpisodesFromYear(TVSeries tvSeries, int year);
}

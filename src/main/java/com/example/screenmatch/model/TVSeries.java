package com.example.screenmatch.model;

import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.service.translation.ITranslation;
import com.example.screenmatch.service.translation.mymemory.MyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity()
@Table(name = "tvseries")
public class TVSeries {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private String plot;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String actors;
    private String poster;

    @Transient
    private List<Episode> episodeList = new ArrayList<>();
    @Transient
    private final ITranslation translation = new MyMemory();

    public TVSeries(TVSeriesDTO tvSeriesDTO) {
        this.title = tvSeriesDTO.title();
        this.totalSeasons = tvSeriesDTO.totalSeasons();
        this.rating = OptionalDouble.of(Double.parseDouble(tvSeriesDTO.rating())).orElse(0.0);
        this.plot = translation.getTranslation(tvSeriesDTO.plot());
        this.category = Category.fromString(tvSeriesDTO.genre().split(",")[0].trim());
        this.actors = tvSeriesDTO.actors();
        this.poster = tvSeriesDTO.poster();
    }

    public TVSeries() {}

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "TVSeries{" +
                "category=" + category +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", plot='" + plot + '\'' +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}

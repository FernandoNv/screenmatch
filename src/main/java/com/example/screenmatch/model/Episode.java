package com.example.screenmatch.model;
import com.example.screenmatch.dto.EpisodeDTO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode implements Comparable<Episode>{
    private String title;
    private Integer number;
    private Double rating;
    private LocalDate releasedDate;
    private Integer season;

    public Episode(){}

    public Episode(EpisodeDTO episodeDTO, Integer season) {
        this.title = episodeDTO.title();
        this.number = episodeDTO.number();
        try{
            this.rating = Double.valueOf(episodeDTO.rating());
        }catch (NumberFormatException exception){
            this.rating = 0.0;
        }

        try {
            this.releasedDate = LocalDate.parse(episodeDTO.releasedDate());
        } catch (DateTimeParseException exception){
            this.releasedDate = null;
        }

        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "season=" + season + '\'' +
                "title='" + title + '\'' +
                ", number=" + number +
                ", rating=" + rating +
                ", releasedDate=" + releasedDate;
    }

    @Override
    public int compareTo(Episode episode) {
        return Integer.compare(this.season, episode.season);
    }
}

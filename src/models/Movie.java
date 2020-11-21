package models;
import java.time.LocalDateTime;
import java.util.List;

public class Movie extends Program
{
    private double Rating;
    private String DateReleased;
    private List<Actor> Actors;

    public Movie() {
    }

    public Movie(double rating, String dateReleased, List<Actor> actors) {
        Rating = rating;
        DateReleased = dateReleased;
        Actors = actors;
    }

    public Movie(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, double rating, String dateReleased, List<Actor> actors) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
        Rating = rating;
        DateReleased = dateReleased;
        Actors = actors;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getDateReleased() {
        return DateReleased;
    }

    public void setDateReleased(String dateReleased) {
        DateReleased = dateReleased;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }
}

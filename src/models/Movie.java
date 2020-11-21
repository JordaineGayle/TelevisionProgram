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

    public Movie(String programType, String channelName, String title, String image, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, double rating, String dateReleased, List<Actor> actors) {
        super(programType, channelName, title, image, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
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

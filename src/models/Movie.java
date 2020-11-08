package models;
import java.util.List;

public class Movie extends Program
{
    private double Rating;
    private String DateReleased;
    private String Title;
    private List<Actor> Actors;

    public Movie() {
    }

    public Movie(double rating, String dateReleased, String title, List<Actor> actors) {
        Rating = rating;
        DateReleased = dateReleased;
        Title = title;
        Actors = actors;
    }

    public Movie(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, double rating, String dateReleased, String title, List<Actor> actors) {
        super(id, length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType);
        Rating = rating;
        DateReleased = dateReleased;
        Title = title;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }
}

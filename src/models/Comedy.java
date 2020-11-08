package models;

import java.util.List;

public class Comedy extends Program
{
    private String Title;
    private List<Actor> Actors;

    public Comedy() {
    }

    public Comedy(String title, List<Actor> actors) {
        Title = title;
        Actors = actors;
    }

    public Comedy(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String title, List<Actor> actors) {
        super(id, length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType);
        Title = title;
        Actors = actors;
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

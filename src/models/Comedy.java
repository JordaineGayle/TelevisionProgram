package models;

import java.util.List;

public class Comedy extends Program
{
    private List<Actor> Actors;

    public Comedy() {
    }

    public Comedy(List<Actor> actors) {
        Actors = actors;
    }

    public Comedy(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, List<Actor> actors) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
        Actors = actors;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }
}

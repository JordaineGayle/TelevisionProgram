package models;

import java.time.LocalDateTime;
import java.util.List;

public class Comedy extends Program
{
    private List<Actor> Actors;

    public Comedy() {
    }

    public Comedy(List<Actor> actors) {
        Actors = actors;
    }

    public Comedy(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, List<Actor> actors) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
        Actors = actors;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }
}

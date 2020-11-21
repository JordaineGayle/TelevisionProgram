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

    public Comedy(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, List<Actor> actors) {
        super(programType, channelName, title, image, source, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        Actors = actors;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }
}

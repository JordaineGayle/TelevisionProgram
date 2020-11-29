package models;

import java.time.LocalDateTime;
import java.util.List;

public class Comedy extends Program
{
    private List<Actor> Actors;

    public Comedy() {
        ProgramColor = ProgramColor.YELLOW;
    }

    public Comedy(List<Actor> actors) {
        Actors = actors;
        ProgramColor = ProgramColor.YELLOW;
    }

    public Comedy(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, List<Actor> actors) {
        super(programType, channelName, title, image, source, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        super.setProgramColor(ProgramColor.YELLOW);
        Actors = actors;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        Actors = actors;
    }

}

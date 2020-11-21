package models;

import java.time.LocalDateTime;

//general class
public class General extends Program
{
    public General() {
    }

    public General(String programType, String channelName, String title, String image, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime) {
        super(programType, channelName, title, image, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
    }

}

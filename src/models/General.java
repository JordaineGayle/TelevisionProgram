package models;

import java.time.LocalDateTime;

//general class
public class General extends Program
{
    public General() {
    }

    public General(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
    }

}

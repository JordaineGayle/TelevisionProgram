
package models;

import java.time.LocalDateTime;

//news class
public class News extends Program
{
    public News() {
    }

    public News(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime) {
        super(programType, channelName, title, image, source, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        super.setProgramColor(ProgramColor.WHITE);
    }

}

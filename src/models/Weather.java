package models;

import java.time.LocalDateTime;

public class Weather extends Program
{
    private int SeverityRating;

    public Weather() {
    }

    public Weather(int severityRating) {
        SeverityRating = severityRating;
    }

    public Weather(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, int severityRating) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
        SeverityRating = severityRating;
    }

    public int getSeverityRating() {
        return SeverityRating;
    }

    public void setSeverityRating(int severityRating) {
        SeverityRating = severityRating;
    }
}

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

    public Weather(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, int severityRating) {
        super(programType, channelName, title, image, source, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        super.setProgramColor(ProgramColor.GREEN);
        SeverityRating = severityRating;
    }

    public int getSeverityRating() {
        return SeverityRating;
    }

    public void setSeverityRating(int severityRating) {
        SeverityRating = severityRating;
    }
}

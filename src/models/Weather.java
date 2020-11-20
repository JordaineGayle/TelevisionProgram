package models;

public class Weather extends Program
{
    private int SeverityRating;

    public Weather() {
    }

    public Weather(int severityRating) {
        SeverityRating = severityRating;
    }

    public Weather(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, int severityRating) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
        SeverityRating = severityRating;
    }

    public int getSeverityRating() {
        return SeverityRating;
    }

    public void setSeverityRating(int severityRating) {
        SeverityRating = severityRating;
    }
}

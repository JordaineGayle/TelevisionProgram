package models;

//general class
public class General extends Program
{
    public General() {
    }

    public General(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
    }
}

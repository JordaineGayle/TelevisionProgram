
package models;
//news class
public class News extends Program
{
    public News() {
    }

    public News(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
    }

}

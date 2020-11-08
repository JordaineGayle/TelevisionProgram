
package models;
//news class
public class News extends Program
{
    public News() {
    }

    public News(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType) {
        super(id, length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType);
    }
}

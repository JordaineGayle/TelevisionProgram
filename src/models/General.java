package models;

//general class
public class General extends Program
{
    private String  Title;

    public General() {
    }

    public General(String title) {
        Title = title;
    }

    public General(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String title) {
        super(id, length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType);
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

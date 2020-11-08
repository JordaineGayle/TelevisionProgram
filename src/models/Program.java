package models;


public class Program
{
    private int id;
    private double Length;
    private double AirTime;
    private String ShortDescription;
    private boolean ClosedCaption;
    private boolean IsNew;
    private boolean IsLiveBroadcast;
    private ProgramStatus ProgramStatus;
    private ProgramColor ProgramColor;
    private String ProgramType;

    public Program(){};

    public Program(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType) {
        this.id = id;
        Length = length;
        AirTime = airTime;
        ShortDescription = shortDescription;
        ClosedCaption = closedCaption;
        IsNew = isNew;
        IsLiveBroadcast = isLiveBroadcast;
        ProgramStatus = programStatus;
        ProgramColor = programColor;
        ProgramType = programType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public double getAirTime() {
        return AirTime;
    }

    public void setAirTime(double airTime) {
        AirTime = airTime;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public boolean isClosedCaption() {
        return ClosedCaption;
    }

    public void setClosedCaption(boolean closedCaption) {
        ClosedCaption = closedCaption;
    }

    public boolean isNew() {
        return IsNew;
    }

    public void setNew(boolean aNew) {
        IsNew = aNew;
    }

    public boolean isLiveBroadcast() {
        return IsLiveBroadcast;
    }

    public void setLiveBroadcast(boolean liveBroadcast) {
        IsLiveBroadcast = liveBroadcast;
    }

    public models.ProgramStatus getProgramStatus() {
        return ProgramStatus;
    }

    public void setProgramStatus(models.ProgramStatus programStatus) {
        ProgramStatus = programStatus;
    }

    public models.ProgramColor getProgramColor() {
        return ProgramColor;
    }

    public void setProgramColor(models.ProgramColor programColor) {
        ProgramColor = programColor;
    }

    public String getProgramType() {
        return ProgramType;
    }

    public void setProgramType(String programType) {
        ProgramType = programType;
    }
}

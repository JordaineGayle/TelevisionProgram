package models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Program
{
    private String id;
    private String ProgramType;
    private String ChannelName;
    private String Title;
    private String ShortDescription;
    private double Length; //in hours
    private boolean ClosedCaption;
    private ProgramPhase ProgramPhase;
    private ProgramStatus ProgramStatus;
    private ProgramColor ProgramColor;
    private LocalDateTime ProgramAirDateTime;

    public Program(){ this.id = UUID.randomUUID().toString(); };

    public Program(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title) {
        this.id = UUID.randomUUID().toString();
        Length = length;
        ProgramAirDateTime = localDateTime;
        ProgramPhase = programPhase;
        ShortDescription = shortDescription;
        ClosedCaption = closedCaption;
        ProgramStatus = programStatus;
        ProgramColor = programColor;
        ProgramType = programType;
        ChannelName = channelName;
        Title = title;
    }

    public String getId() {
        return id;
    }

    public void setid(){
        this.id = UUID.randomUUID().toString();
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public LocalDateTime getProgramAirDateTime() {
        return ProgramAirDateTime;
    }

    public void setProgramAirDateTime(LocalDateTime programAirDateTime) {
        ProgramAirDateTime = programAirDateTime;
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

    public models.ProgramPhase getProgramPhase() {
        return ProgramPhase;
    }

    public void setProgramPhase(models.ProgramPhase programPhase) {
        ProgramPhase = programPhase;
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

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

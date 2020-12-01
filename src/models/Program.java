/**
 * This class is a parent and also a child class, it act as the instance of the IProgram since the IProgram can't be instantiated
 * We could however remove IProgram and implement all the methods here and override them in the classes they are used but we wanted
 * to show that we understood parent of parent, sub parent etc.
 *
 * */

package models;


import interfaces.IProgram;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Program implements IProgram
{
    private String id;
    private String ProgramType;
    private String ChannelName;
    private String Title;
    private String Image;
    private String Source;
    private String ShortDescription;
    private double Length; //in hours
    private double Duration; //in days
    private boolean ClosedCaption;
    private ProgramPhase ProgramPhase;
    private ProgramStatus ProgramStatus;
    protected ProgramColor ProgramColor;
    private LocalDateTime ProgramAirDateTime;

    public Program(){ this.id = UUID.randomUUID().toString(); };

    public Program(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime) {
        this.id = UUID.randomUUID().toString();
        ProgramType = programType;
        ChannelName = channelName;
        Title = title;
        Image = image;
        Source = source;
        ShortDescription = shortDescription;
        Length = length;
        Duration = duration;
        ClosedCaption = closedCaption;
        ProgramPhase = programPhase;
        ProgramStatus = programStatus;
        ProgramColor = programColor;
        ProgramAirDateTime = programAirDateTime;
    }

    public String getId() {
        return id;
    }

    public void setid(){
        this.id = UUID.randomUUID().toString();
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public double getDuration() {
        return Duration;
    }

    public void setDuration(double duration) {
        Duration = duration;
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

    public LocalDateTime getProgramAirDateTime() {
        return ProgramAirDateTime;
    }

    public void setProgramAirDateTime(LocalDateTime programAirDateTime) {
        ProgramAirDateTime = programAirDateTime;
    }

    @Override
    public List<Actor> getActors() {
        return null;
    }

    @Override
    public void setActors(List<Actor> actors) {

    }

    @Override
    public Denomination getDenomination() {
        return null;
    }

    @Override
    public void setDenomination(Denomination denomination) {

    }

    @Override
    public Range getAgeRange() {
        return null;
    }

    @Override
    public void setAgeRange(Range ageRange) {

    }

    @Override
    public double getRating() {
        return 0;
    }

    @Override
    public void setRating(double rating) {

    }

    @Override
    public LocalDateTime getDateReleased() {
        return null;
    }

    @Override
    public void setDateReleased(LocalDateTime dateReleased) {

    }

    @Override
    public double getSeverityRating() {
        return 0;
    }

    @Override
    public void setSeverityRating(double severityRating) {

    }
}

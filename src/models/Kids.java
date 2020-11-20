package models;

import javax.swing.border.TitledBorder;

//Kids class
public class Kids extends Program
{
    private Range AgeRange;

    public Kids() {
    }

    public Kids(Range ageRange) {
        AgeRange = ageRange;
    }

    public Kids(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, Range ageRange) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
        AgeRange = ageRange;
    }

    public Range getAgeRange() {
        return AgeRange;
    }

    public void setAgeRange(Range ageRange) {
        AgeRange = ageRange;
    }
}

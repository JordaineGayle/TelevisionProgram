package models;

import javax.swing.border.TitledBorder;

//Kids class
public class Kids extends Program
{
    private String Title;
    private Range AgeRange;

    public Kids() {
    }

    public Kids(String title, Range ageRange) {
        Title = title;
        AgeRange = ageRange;
    }

    public Kids(int id, double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String title, Range ageRange) {
        super(id, length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor);
        Title = title;
        AgeRange = ageRange;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Range getAgeRange() {
        return AgeRange;
    }

    public void setAgeRange(Range ageRange) {
        AgeRange = ageRange;
    }
}

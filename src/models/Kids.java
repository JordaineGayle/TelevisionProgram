package models;

import javax.swing.border.TitledBorder;
import java.time.LocalDateTime;

//Kids class
public class Kids extends Program
{
    private Range AgeRange;

    public Kids() {
    }

    public Kids(Range ageRange) {
        AgeRange = ageRange;
    }

    public Kids(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, Range ageRange) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
        AgeRange = ageRange;
    }

    public Range getAgeRange() {
        return AgeRange;
    }

    public void setAgeRange(Range ageRange) {
        AgeRange = ageRange;
    }
}

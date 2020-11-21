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

    public Kids(String programType, String channelName, String title, String image, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, Range ageRange) {
        super(programType, channelName, title, image, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        AgeRange = ageRange;
    }

    public Range getAgeRange() {
        return AgeRange;
    }

    public void setAgeRange(Range ageRange) {
        AgeRange = ageRange;
    }
}

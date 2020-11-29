package models;

import java.time.LocalDateTime;

//Kids class
public class Kids extends Program
{
    private Range AgeRange;

    public Kids() {
        ProgramColor = ProgramColor.PURPLE;
    }

    public Kids(Range ageRange) {
        AgeRange = ageRange;
        ProgramColor = ProgramColor.PURPLE;
    }

    public Kids(String programType, String channelName, String title, String image, String source, String shortDescription, double length, double duration, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, LocalDateTime programAirDateTime, Range ageRange) {
        super(programType, channelName, title, image, source, shortDescription, length, duration, closedCaption, programPhase, programStatus, programColor, programAirDateTime);
        super.setProgramColor(ProgramColor.PURPLE);
        AgeRange = ageRange;
    }

    public Range getAgeRange() {
        return AgeRange;
    }

    public void setAgeRange(Range ageRange) {
        AgeRange = ageRange;
    }
}

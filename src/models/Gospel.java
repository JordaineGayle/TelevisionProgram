package models;

import java.time.LocalDateTime;

public class Gospel extends Program {

    private Denomination Denomination;

    public Gospel() {
    }

    public Gospel(models.Denomination denomination) {
        Denomination = denomination;
    }

    public Gospel(double length, LocalDateTime localDateTime, String shortDescription, boolean closedCaption, models.ProgramPhase programPhase, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, models.Denomination denomination) {
        super(length, localDateTime, shortDescription, closedCaption, programPhase, programStatus, programColor, programType, channelName, title);
        Denomination = denomination;
    }

    public models.Denomination getDenomination() {
        return Denomination;
    }

    public void setDenomination(models.Denomination denomination) {
        Denomination = denomination;
    }
}

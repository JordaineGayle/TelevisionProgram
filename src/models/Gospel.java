package models;

public class Gospel extends Program {

    private Denomination Denomination;

    public Gospel() {
    }

    public Gospel(models.Denomination denomination) {
        Denomination = denomination;
    }

    public Gospel(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, models.Denomination denomination) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName, title);
        Denomination = denomination;
    }

    public models.Denomination getDenomination() {
        return Denomination;
    }

    public void setDenomination(models.Denomination denomination) {
        Denomination = denomination;
    }
}

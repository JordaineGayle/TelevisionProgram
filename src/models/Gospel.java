package models;

public class Gospel extends Program {

    private String Title;
    private Denomination Denomination;

    public Gospel() {
    }

    public Gospel(String title, models.Denomination denomination) {
        Title = title;
        Denomination = denomination;
    }

    public Gospel(double length, double airTime, String shortDescription, boolean closedCaption, boolean isNew, boolean isLiveBroadcast, models.ProgramStatus programStatus, models.ProgramColor programColor, String programType, String channelName, String title, models.Denomination denomination) {
        super(length, airTime, shortDescription, closedCaption, isNew, isLiveBroadcast, programStatus, programColor, programType, channelName);
        Title = title;
        Denomination = denomination;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public models.Denomination getDenomination() {
        return Denomination;
    }

    public void setDenomination(models.Denomination denomination) {
        Denomination = denomination;
    }
}

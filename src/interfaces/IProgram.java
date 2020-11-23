package interfaces;

import models.Actor;
import models.Program;
import models.Range;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IProgram {

    String getId();

    List<Actor> getActors();

    void setActors(List<Actor> actors);

    models.Denomination getDenomination();

    void setDenomination(models.Denomination denomination);

    Range getAgeRange();

    void setAgeRange(Range ageRange);

    double getRating();

    void setRating(double rating);

    LocalDateTime getDateReleased();

    void setDateReleased(LocalDateTime dateReleased);

    int getSeverityRating();

    void setSeverityRating(int severityRating);


    void setid();

    String getProgramType();

    void setProgramType(String programType);

    String getChannelName();

    void setChannelName(String channelName);

    String getTitle();

    void setTitle(String title);

    String getImage();

    void setImage(String image);

    String getSource();

    void setSource(String source);

    String getShortDescription();

    void setShortDescription(String shortDescription);

    double getLength();

    void setLength(double length);

    double getDuration();

    void setDuration(double duration);

    boolean isClosedCaption();

    void setClosedCaption(boolean closedCaption);

    models.ProgramPhase getProgramPhase();

    void setProgramPhase(models.ProgramPhase programPhase);

    models.ProgramStatus getProgramStatus();

    void setProgramStatus(models.ProgramStatus programStatus);

    models.ProgramColor getProgramColor();

    void setProgramColor(models.ProgramColor programColor);

    LocalDateTime getProgramAirDateTime();

    void setProgramAirDateTime(LocalDateTime programAirDateTime);

}

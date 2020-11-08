package models;

import java.security.PublicKey;

//actor class
public class Actor
{
    private int Id;

    private String FirstName;
    private String LastName;
    private int Age;
    private String LastGrammyDate;

    public Actor(){};

    public Actor(int id, String firstName, String lastName, int age, String lastGrammyDate) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        LastGrammyDate = lastGrammyDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getLastGrammyDate() {
        return LastGrammyDate;
    }

    public void setLastGrammyDate(String lastGrammyDate) {
        LastGrammyDate = lastGrammyDate;
    }
}

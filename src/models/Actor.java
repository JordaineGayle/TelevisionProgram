package models;

import java.util.UUID;

//actor class
public class Actor
{
    private String Id;

    private String FirstName;
    private String LastName;
    private int Age;
    private String LastGrammyDate;

    public Actor(){Id = UUID.randomUUID().toString();};

    public Actor(String id, String firstName, String lastName, int age, String lastGrammyDate) {
        Id = UUID.randomUUID().toString();
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        LastGrammyDate = lastGrammyDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        if(id.isEmpty()){
            Id = UUID.randomUUID().toString();
        }else{
            Id = id;
        }
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

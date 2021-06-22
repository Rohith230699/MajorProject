package com.example.registerloginui;

public class model
{
  String RollNumber,Email_Id;

    public model() {
    }

    public model(String RollNumber, String Email_id) {
        this.RollNumber = RollNumber;
        this.Email_Id = Email_id;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public void setRollNumber(String RollNumber) {
        this.RollNumber = RollNumber;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String Email_Id) {
        this.Email_Id = Email_Id;
    }
}

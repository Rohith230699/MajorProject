package com.example.registerloginui;

public class Notifications {

    String Title,Note;

    public Notifications() {
    }

    public Notifications(String title, String note) {
        Title = title;
        Note = note;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}

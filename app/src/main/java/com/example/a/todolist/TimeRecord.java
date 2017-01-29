package com.example.a.todolist;

public class TimeRecord {
    private String time;
    private String notes;
    private String date;
    public TimeRecord(String time, String notes, String date) {
        this.time = time;
        this.date = date;
        this.notes = notes;

    }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; } }

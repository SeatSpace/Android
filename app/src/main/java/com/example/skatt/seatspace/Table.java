package com.example.skatt.seatspace;

public class Table {
    private int id;
    private boolean isTaken;
    private String time;
    public Table(int ID, boolean IsTaken, String timeStamp) {
        this.id = ID;
        this.isTaken = IsTaken;
        this.time = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

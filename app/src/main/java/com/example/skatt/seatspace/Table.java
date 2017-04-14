package com.example.skatt.seatspace;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Table {
    private int id, x,y,width, height;
    private boolean isTaken;
    private String time;
    private TextView table;


    public Table(int ID, boolean IsTaken, String timeStamp, TextView table) {
        this.id = ID;
        this.isTaken = IsTaken;
        this.time = timeStamp;
        this.table = table;
        setTaken(isTaken);
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
        if(taken){
            table.setBackgroundColor(Color.RED);
        } else {
            table.setBackgroundColor(Color.GREEN);
        }
        isTaken = taken;
    }

    public void setTable(int x, int y, int width, int height){
        // Sets the parameters for the table text view
        ViewGroup.MarginLayoutParams paramsMargin = new ViewGroup.MarginLayoutParams(width, height);
        paramsMargin.setMargins(x, y, 0 , 0);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(paramsMargin);
        table.setLayoutParams(params);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

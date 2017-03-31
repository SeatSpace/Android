package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*
Home Screen class has 2 buttons allowing you to choose how you'd like to find available space
 */
public class HomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_homescreen);
    }

    /*
    Takes you to the form where you can select the Room
    to search for seats in
     */
    public void ChooseRoom(View view) {
        Intent i = new Intent(this, ChooseRoom.class);
        startActivity(i);
    }

    /*
    Takes you to the form where you can select how many people
    you wish to work with and search for a room with that many spaces
     */
    public void ListAvailableRooms(View view) {
        Intent i = new Intent(this, ListAvailableRooms.class);
        startActivity(i);
    }
}

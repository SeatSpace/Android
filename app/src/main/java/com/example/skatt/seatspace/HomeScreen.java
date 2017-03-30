package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_homescreen);
    }
    public void RoomOption(View view)
    {
        Intent i = new Intent(this, ChooseRoom.class);
        startActivity(i);
    }

    public void CheckRooms(View view)
    {
        Intent i = new Intent(this, ListAvailableRooms.class);
        startActivity(i);
    }
}

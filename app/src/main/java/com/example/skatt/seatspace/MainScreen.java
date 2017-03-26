package com.example.skatt.seatspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

public class MainScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
    }
    public void RoomOption()
    {
        Intent i = new Intent(this, RoomOption.class);
        startActivity(i);
    }

    public void SearchRooms()
    {
        Intent i = new Intent(this, CheckRooms.class);
        startActivity(i);
    }
}

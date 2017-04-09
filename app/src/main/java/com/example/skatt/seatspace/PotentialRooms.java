package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PotentialRooms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potential_rooms);
    }

    public void view(View view) {
        Intent i = new Intent(this, DynamicFloor.class);
        startActivity(i);
    }


    public void home(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

}

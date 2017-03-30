package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

//

public class RoomOption extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Room");
        setContentView(R.layout.activity_room);

        addItemsOnBSpinner();
        addItemsOnFSpinner();
        addItemsOnRSpinner();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void CheckSeats(View view)
    {
        Spinner b = (Spinner)findViewById(R.id.spinnerB);
        String building = b.getSelectedItem().toString();
        Spinner f = (Spinner)findViewById(R.id.spinnerF);
        String floor = f.getSelectedItem().toString();
        Spinner r = (Spinner)findViewById(R.id.spinnerR);
        String room = r.getSelectedItem().toString();

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Building", building);
        i.putExtra("Floor", floor);
        i.putExtra("Room", room);
        startActivity(i);
    }

    public void addItemsOnBSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerB);
        ArrayList<String> Buildings = new ArrayList<>();
        Buildings.add("Library");
        Buildings.add("1 West");
        Buildings.add("10 West");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoomOption.this, R.layout.spinner_item, Buildings);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addItemsOnFSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerF);
        ArrayList<String> Floors = new ArrayList<>();
        Floors.add("Floor 1");
        Floors.add("Floor 2");
        Floors.add("Floor 3");
        Floors.add("Floor 4");
        Floors.add("Floor 5");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoomOption.this, R.layout.spinner_item, Floors);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Floors);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
    }

    public void addItemsOnRSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerR);
        ArrayList<String> Rooms = new ArrayList<>();
        Rooms.add("2.101");
        Rooms.add("2.102");
        Rooms.add("2.103");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoomOption.this, R.layout.spinner_item, Rooms);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Rooms);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
    }
}
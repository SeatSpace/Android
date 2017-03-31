package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/*
    ChooseRoom Class to select the room you wish to see the available seats for
 */

public class ChooseRoom extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Room");
        setContentView(R.layout.activity_chooseroom);

        addItemsOnBSpinner();
        addItemsOnFSpinner();
        addItemsOnRSpinner();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /*
    Spinners with room options are called and then sent to the Floor Layout Class
     */
    public void CheckSeats(View view) {
        Spinner b = (Spinner) findViewById(R.id.spinnerB);
        String building = b.getSelectedItem().toString();
        Spinner f = (Spinner) findViewById(R.id.spinnerF);
        String floor = f.getSelectedItem().toString();
        Spinner r = (Spinner) findViewById(R.id.spinnerR);
        String room = r.getSelectedItem().toString();

        Intent i = new Intent(this, FloorLayout.class);
        i.putExtra("Building", building);
        i.putExtra("Floor", floor);
        i.putExtra("Room", room);
        startActivity(i);
    }

    /*
    Adding the different options to the spinners

    ################ HARD CODED #####################

 */
    public void addItemsOnBSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerB);
        ArrayList<String> Buildings = new ArrayList<>();
        Buildings.add("Library");
        Buildings.add("1 West");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseRoom.this, R.layout.spinner_item, Buildings);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addItemsOnFSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerF);
        ArrayList<String> Floors = new ArrayList<>();
        Floors.add("Floor 2");
        Floors.add("Floor 3");
        Floors.add("Floor 4");
        Floors.add("Floor 5");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseRoom.this, R.layout.spinner_item, Floors);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addItemsOnRSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerR);
        ArrayList<String> Rooms = new ArrayList<>();
        Rooms.add("2.103");
        Rooms.add("Main Floor");
        Rooms.add("Group Study Room");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseRoom.this, R.layout.spinner_item, Rooms);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /*
    Home button takes you back to the Home Screen form
     */
    public void home(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

}
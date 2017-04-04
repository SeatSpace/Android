package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListAvailableRooms extends AppCompatActivity {
    TextView rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listavailablerooms);
        addItemsOnSpinner();
        //rooms = (TextView) findViewById(R.id.freeRooms);
        Intent i = getIntent();
    }

    private void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerNo);
        ArrayList<String> Numbers = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Numbers.add(String.valueOf(i));
        }
        Numbers.add("8+");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Numbers);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.floorPref);
        ArrayList<String> floors = new ArrayList<>();
        floors.add("None");
        for (int i = 1; i < 6; i++) {
            floors.add("Floor "+i);
        }
        ArrayAdapter<String> adapater = new ArrayAdapter<String>(this, R.layout.spinner_item, floors);
        adapater.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapater);

        Spinner spinner2 = (Spinner) findViewById(R.id.buildingPref);
        ArrayList<String> buildings = new ArrayList<>();
        buildings.add("Library");
        buildings.add("Chancellor Building");
        adapater = new ArrayAdapter<String>(this, R.layout.spinner_item, buildings);
        adapater.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapater);

    }

    public void SearchRooms(View view) {
        rooms.setText("");
        Spinner num = (Spinner) findViewById(R.id.spinnerNo);
        int peopleNo = Integer.parseInt(num.getSelectedItem().toString());
        try {
            // Create a URL for the desired page
            URL url = new URL("http://kkmonlee.com/launchpad/file.txt");
            URLConnection connection = url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            while ((str = in.readLine()) != null) {
                String[] location = str.split(",");
                int currentSeats = Integer.parseInt(location[3]);
                int totalSeats = Integer.parseInt(location[4]);
                if (currentSeats >= peopleNo) {
                    rooms.append(str + "\n");
                }
            }
            in.close();
        } catch (MalformedURLException e) {
            System.out.println("YOU FUCKED UP.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("YOU FUCKED UP.");
            e.printStackTrace();
        }
        // read from the main file and return the avalaible spaces

        // if itts >= what they want then add to array list
    }
    public void home(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

}

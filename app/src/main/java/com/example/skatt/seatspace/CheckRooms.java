package com.example.skatt.seatspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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

public class CheckRooms extends AppCompatActivity {
    TextView rooms = (TextView) findViewById(R.id.freeRooms);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addItemsOnSpinner();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_checkrooms);
    }

    private void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerNo);
        ArrayList<Integer> Numbers = new ArrayList<>();
        for(int i=1;i<9;i++)
        {
            Numbers.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Numbers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void CheckRooms(View view)
    {
        Spinner num = (Spinner)findViewById(R.id.spinnerNo);
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
                if(currentSeats >= peopleNo)
                {
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
}

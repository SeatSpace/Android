package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView availableNo;
    TextView totalNo;
    int seats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        final String building = i.getStringExtra("Building");
        final String floor = i.getStringExtra("Floor");
        final String room = i.getStringExtra("Room");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        availableNo = (TextView) findViewById(R.id.availableNo);
        totalNo = (TextView) findViewById(R.id.totalNo);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                while(seats<20) {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getFile(building, floor, room); // put the building floor and room in to the earch
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void getFile(String b, String f, String r) throws Exception {
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
                if(location[0].equals(b) && location[1].equals(f) && location[2].equals(r))
                {
                    if(totalSeats < currentSeats)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setMessage("You cannot possibly leave the room.").setTitle("Room is empty");
                        totalNo.setText(location[4]);
                        availableNo.setText(location[4]);
                    }
                    else if(currentSeats < 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setMessage("You cannot enter this room.").setTitle("Room is full");
                        totalNo.setText(location[4]);
                        availableNo.setText("0");
                    }
                    else
                    {
                        totalNo.setText(location[4]);
                        availableNo.setText(location[3]);
                    }

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
    }
}
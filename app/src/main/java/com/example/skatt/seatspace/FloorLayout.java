package com.example.skatt.seatspace;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/*
Floor plan for the room selected in the previous form,
updated by the Arduinos from the server
 */
public class FloorLayout extends AppCompatActivity {

    TextView availableNo;
    TextView totalNo;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    ArrayList<Button> buttons = new ArrayList<>();
    private int total = 54;
    private int available = 54;
    private Boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Seat Space- View");
        setContentView(R.layout.activity_floorlayout);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);

        addButtons();
        Intent i = getIntent();
        final String building = i.getStringExtra("Building");
        final String floor = i.getStringExtra("Floor");
        final String room = i.getStringExtra("Room");

        availableNo = (TextView) findViewById(R.id.availableNo);
        totalNo = (TextView) findViewById(R.id.totalNo);
        running = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // What the fuck is happening here?
                                long startTime = System.currentTimeMillis();
                                long endTime = startTime + 500;
                                if (startTime > endTime) {
                                    getFile(building, floor, room); // put the building floor and room in to the earch
                                    updateButtons();
                                    endTime = startTime + 500;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    /*
    ################# HARD CODED ############# 
						
    Hard coded adding to array list of buttons
     */
    private void addButtons() {
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);
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
                totalNo.setText(location[4]);
                if (location[0].equals(b) && location[1].equals(f) && location[2].equals(r)) {
                    if (totalSeats < currentSeats) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("You cannot possibly leave the room.").setTitle("Room is empty");
                        availableNo.setText(location[4]);
                    } else if (currentSeats < 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setMessage("You cannot enter this room.").setTitle("Room is full");
                        availableNo.setText("0");
                    } else {
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

    public void updateButtons() {
        try {
            // Create a URL for the desired page
            URL url = new URL("http://kkmonlee.com/launchpad/arduino1.txt");
            URLConnection connection = url.openConnection();

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;

            while ((str = in.readLine()) != null) {
                String[] inputArray = str.split(",");

                for (int i = 0; i < inputArray.length; i++) {
                    // Gets the background color of the button
                    ColorDrawable color = (ColorDrawable) buttons.get(i).getBackground();
                    if (inputArray[i].equals("1")) {
                        // Checks if the availability has changed
                        if (color.getColor() == getResources().getColor(R.color.roomGreen)) {
                            available--;
                        }
                        buttons.get(i).setBackgroundColor(Color.RED);
                    } else if (inputArray[i].equals("0")) {
                        // Checked if the availability has changed
                        if (color.getColor() == Color.RED) {
                            available++;
                        }
                        buttons.get(i).setBackgroundColor(getResources().getColor(R.color.roomGreen));
                    }
                }
                availableNo.setText(String.valueOf(available));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("YOU FUCKED UP.");
        }
    }

    /*
    Home button
     */
    public void home(View view) {
        running = false;
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);

    }
}

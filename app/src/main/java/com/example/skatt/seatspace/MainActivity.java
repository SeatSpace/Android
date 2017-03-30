package com.example.skatt.seatspace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

    int seats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Seat Space- View");
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        addButtons();
        Intent i = getIntent();
        final String building = i.getStringExtra("Building");
        final String floor = i.getStringExtra("Floor");
        final String room = i.getStringExtra("Room");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                                    updateBtns();
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
    private int total = 54;
    private int available = 54;
    public void updateBtns() throws Exception {
        for(int i=1; i<3; i++)
        {
            try {
                // Create a URL for the desired page
                URL url = new URL("http://kkmonlee.com/launchpad/arduino" + i + ".txt"); // ###################### get real file name
                if(i==1)
                {
                    URLConnection connection = url.openConnection();

                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String str;

                    while ((str = in.readLine()) != null) {
                        if(str.equals("1"))
                        {
                            btn1.setBackgroundColor(Color.RED);
                            available--;
                            availableNo.setText(String.valueOf(available));
                        }
                        else
                        {
                            btn1.setBackgroundColor(Color.GREEN);
                            available++;
                            availableNo.setText(String.valueOf(available));
                        }
                    }
                    in.close();
                }
                if(i==2)
                {
                    URLConnection connection = url.openConnection();

                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String str;

                    while ((str = in.readLine()) != null) {
                        if(str.equals("1"))
                        {
                            btn2.setBackgroundColor(Color.RED);
                        }
                        else
                        {
                            btn2.setBackgroundColor(Color.GREEN);
                        }
                    }
                    in.close();
                }

            } catch (MalformedURLException e) {
                System.out.println("YOU FUCKED UP.");
            } catch (IOException e) {
                System.out.println("YOU FUCKED UP.");
            }
        }

    }

}
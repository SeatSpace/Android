package com.example.skatt.seatspace;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
Floor plan for the room selected in the previous form,
updated by the Arduinos from the server
 */
public class FloorLayout extends AppCompatActivity {

    TextView availableNo;
    TextView totalNo;

    TextView btn1;
    TextView btn2;
    TextView btn3;
    TextView btn4;
    TextView btn5;
    TextView btn6;
    TextView btn7;
    TextView btn8;
    TextView btn9;

    ArrayList<TextView> buttons = new ArrayList<>();
    ArrayList<Table> tables = new ArrayList<>();
    private int total = 54;
    private int available = 54;
    private Boolean running;
    private Thread thread;
    private BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSQLData(); // gets intial data to load the form

        setTitle("Room View");
        setContentView(R.layout.activity_floorlayout2);
        btn1 = (TextView) findViewById(R.id.btn1);
        btn2 = (TextView) findViewById(R.id.btn2);
        btn3 = (TextView) findViewById(R.id.btn3);
        btn4 = (TextView) findViewById(R.id.btn4);
        btn5 = (TextView) findViewById(R.id.btn5);
        btn6 = (TextView) findViewById(R.id.btn6);
        btn7 = (TextView) findViewById(R.id.btn7);
        btn8 = (TextView) findViewById(R.id.btn8);

        addButtons();
        Intent i = getIntent();
        final String building = i.getStringExtra("Building");
        final String floor = i.getStringExtra("Floor");
        final String room = i.getStringExtra("Room");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        availableNo = (TextView) findViewById(R.id.availableNo);
        totalNo = (TextView) findViewById(R.id.totalNo);

        running = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // gets the data from the file
                                    getFile(building, floor, room); // put the building floor and room in to the search
                                    updateTables();

                                    // same as update buttons but with SQL
                                    // will need to pass the room number in as the query when more rooms are in the database
                                    // getSQLData(/* Building/room number as query*/);
                                    // updateButtonsWithSQL();



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
            availableNo = (TextView) findViewById(R.id.availableNo);
            totalNo = (TextView) findViewById(R.id.totalNo);
            while ((str = in.readLine()) != null) {
                //System.out.println(str);
                String[] location = str.split(",");
                int currentSeats = Integer.parseInt(location[3]);
                int totalSeats = Integer.parseInt(location[4]);
                totalNo.setText(location[4]);
                //System.out.println(Arrays.toString(location));
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

    public void updateTables() {
        try {
            // Create a URL for the desired page
            URL url = new URL("http://kkmonlee.com/launchpad/arduino1.txt");
            URLConnection connection = url.openConnection();

            // Read all the text returned by the server
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;

            while ((str = in.readLine()) != null) {
                String[] inputArray = str.split(",");
                //System.out.println(str);
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
        finish();
        //thread.interrupt();
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    public void updateButtonsWithSQL() {
        try {
            // loop through the arraylist of tables
            for (int i = 0; i < tables.size(); i++) {
                // Gets the background color of the button
                ColorDrawable color = (ColorDrawable) buttons.get(i).getBackground();
                // if the table's taken boolean is true then change to red
                if (tables.get(i).isTaken()) {
                    // Checks if the availability has changed
                    if (color.getColor() == getResources().getColor(R.color.roomGreen)) {
                        available--;
                    }
                    buttons.get(i).setBackgroundColor(Color.RED);

                } else if (!tables.get(i).isTaken()) { // if the table's taken boolean is false then change to green
                    // Checked if the availability has changed
                    if (color.getColor() == Color.RED) {
                        available++;
                    }
                    buttons.get(i).setBackgroundColor(getResources().getColor(R.color.roomGreen));
                }
            }
            availableNo.setText(String.valueOf(available));
        } catch (Exception e) {
            System.out.println("YOU FUCKED UP.");
        }
    }


    public void getSQLData() {
        //Its in a thread because android studio is a bitch
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    // URL of the database and the table (kkmonlee_seatspace)
                    String url = "jdbc:mysql://74.220.219.118:3306/kkmonlee_seatspace";
                    // Starts a connection with the URL above
                    // getConnection(URL, username, password)
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(url, "kkmonlee_insert", "seatspace");
                    System.out.println("Database connection established");

                    // Create a statement object.
                    Statement statement = conn.createStatement();
                    // Make an SQL query to select everything from table Floor3
                    String query = "SELECT * FROM Floor3";
                    // Execute the query and store the results in a set
                    ResultSet rs = statement.executeQuery(query);

                    // clears the old data to read in the next set
                    tables.clear();

                    // While the next set exists...
                    while (rs.next()) {
                        // Get the 3 columns
                        String id = rs.getObject(1).toString();
                        String taken = rs.getObject(2).toString();
                        String time = rs.getObject(3).toString();

                        Table table = new Table(Integer.parseInt(id), Boolean.parseBoolean(taken), time, new TextView(FloorLayout.this));

                        // Linear Search algorithm
                        /*boolean doesExist = false;
                        for (Table t : tables) {
                            if (table.getId() == t.getId()) {
                                doesExist = true;
                                break;
                            } else {
                            }
                        }
                        if (!doesExist) {
                            tables.add(table);
                        }*/

                        tables.add(table);

                        // And print them
                        System.out.println("ID: " + id);
                        System.out.println("Taken: " + taken);
                        System.out.println("Time: " + time);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                            System.out.println("Database connection terminated");
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        });
        thread.start();
    }
}
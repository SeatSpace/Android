package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DynamicFloor extends AppCompatActivity {

    HashMap<Integer, Table> tables = new HashMap();
    private TextView availableNo;
    private TextView totalNo;
    private int available;
    private boolean running;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Room View");
        setContentView(R.layout.activity_dynamic_floor);
        Intent i = getIntent();
        final String building = i.getStringExtra("Building");
        final String floor = i.getStringExtra("Floor");
        final String room = i.getStringExtra("Room");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        availableNo = (TextView) findViewById(R.id.availableNo);
        totalNo = (TextView) findViewById(R.id.totalNo);
        getSQLData();
    }

    /*
    Home button
     */
    public void home(View view) {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }

    public void getSQLData() {
        //Its in a thread because android studio is a bitch
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
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
                        // While the next set exists...
                        while (rs.next()) {
                            // Get the 3 columns
                            final String id = rs.getObject(1).toString();
                            final String taken = rs.getObject(2).toString();
                            final String time = rs.getObject(3).toString();
                            // Checks if the table has been added
                            if(!tables.containsKey(Integer.parseInt(id))) {
                                // Adds the table to the Frame Layout
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Creates the text view
                                        TextView tableTextView = new TextView(DynamicFloor.this);
                                        Table table = new Table(Integer.parseInt(id), Boolean.parseBoolean(taken), time, tableTextView);
                                        tables.put(Integer.parseInt(id), table);
                                        // Sets the parameters for the table
                                        table.setTable(50, 100, 100, 100);
                                        // Adds the table to the FrameLayout
                                        ((FrameLayout) findViewById(R.id.tableFrame)).addView(tableTextView);
                                    }
                                });
                            } else {
                                // Changes the colour of the table if the availability has changed
                                tables.get(Integer.parseInt(id)).setTaken(Boolean.parseBoolean(taken));
                                tables.get(Integer.parseInt(id)).setTime(time);
                            }
                            // And print them
                            System.out.println("ID: " + id);
                            System.out.println("Taken: " + taken);
                            System.out.println("Time: " + time);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                availableNo.setText(Integer.toString(tables.size()));
                                totalNo.setText(Integer.toString(tables.size()));
                            }
                        });

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
            }
        });
        thread.start();
    }



}

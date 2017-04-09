package com.example.skatt.seatspace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class DynamicFloor extends AppCompatActivity {

    ArrayList<Table> tables = new ArrayList<>();
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

        running = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getSQLData();
                while (running) {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //getSQLData();
                                    //Update stuff
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
                    // While the next set exists...
                    while (rs.next()) {
                        // Get the 3 columns
                        String id = rs.getObject(1).toString();
                        String taken = rs.getObject(2).toString();
                        String time = rs.getObject(3).toString();
                        Table table = new Table(Integer.parseInt(id), Boolean.parseBoolean(taken), time);
                        tables.add(table);
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
        });
        thread.start();
    }



}

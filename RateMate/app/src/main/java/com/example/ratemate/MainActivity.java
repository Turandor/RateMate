package com.example.ratemate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button addData;
    Button viewData;
    Button viewStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        addData = (Button)findViewById(R.id.addData);
        viewData = (Button)findViewById(R.id.viewData);
        viewStatistics = (Button)findViewById(R.id.viewStatistics);


        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCultureText.class);
                startActivity(intent);
            }
        });
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabaseViewActivity.class);
                startActivity(intent);
            }
        });
        viewStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
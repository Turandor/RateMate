package com.example.ratemate;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StatisticsViewActivity extends AppCompatActivity{

    DatabaseHelper db;
    Spinner categorySpinner;
    TextView countText;
    TextView averageRateText;

    String[] categories = { "All", "Film", "TV Series", "Book", "Comic book", "Computer Game", "Music Album", "Theater performance", "Poem" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_view);

        db = new DatabaseHelper(this);

        categorySpinner = findViewById(R.id.catSpinner);
        countText = findViewById(R.id.countText);
        averageRateText = findViewById(R.id.averageRateText);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                viewStatistics(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void viewStatistics(String category)
    {
        Cursor cursor;
        float averageRate = 0;
        int count = 0;

        if (category == "All")
            cursor = db.viewData();
        else
            cursor = db.viewData(category);

        if (cursor.getCount() == 0){
            countText.setText("-");
            averageRateText.setText("-");
            Toast.makeText(this,"No statistics to show", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()){
                averageRate += cursor.getFloat(2);
                count++;
            }
            countText.setText(String.valueOf(count));
            averageRate = averageRate/count;
            averageRateText.setText(String.valueOf(averageRate));
        }
    }

}

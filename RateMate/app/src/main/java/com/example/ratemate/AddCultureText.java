package com.example.ratemate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class AddCultureText extends AppCompatActivity {

    DatabaseHelper database;

    /* ADD ACTIVITY */
    Button addData;
    Button viewData;

    /* EDIT ACTIVITY */
    Button editData;
    Button deleteData;
    TextView rateDateText;

    TextView nameText;
    TextView releaseDateText;
    EditText descriptionMultiLine;
    EditText reviewMultiLines;
    Button choseDateButton;
    Spinner categorySpinner;
    RatingBar ratingBar;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    String[] categories = { "Film", "TV Series", "Book", "Comic book", "Computer Game", "Music Album", "Theater performance", "Poem" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();

        /* IF EDIT CULTURE TEXT ACTIVITY */
        if(receivedIntent.getIntExtra("id",-1) > 0) {
            setContentView(R.layout.edit_culture_text);
            editData = findViewById(R.id.saveData);
            deleteData = findViewById(R.id.deleteData);
            rateDateText = findViewById(R.id.rateDateText);
        }

        /* IF ADD CULTURE TEXT ACTIVITY */
        else {
            setContentView(R.layout.add_culture_text);
            viewData = findViewById(R.id.viewData);
            addData = findViewById(R.id.addData);
        }

        database = new DatabaseHelper(this);

        releaseDateText = (TextView)findViewById(R.id.releaseDateText);
        nameText = (TextView)findViewById(R.id.nameText);
        choseDateButton = (Button)findViewById(R.id.choseDateButton);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        descriptionMultiLine = findViewById(R.id.descriptionMultiLine);
        reviewMultiLines = findViewById(R.id.reviewMultiLines);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /* IF EDIT CULTURE TEXT ACTIVITY */
        if(receivedIntent.getIntExtra("id",-1) > 0) {
            rateDateText.setText(receivedIntent.getStringExtra("rate_date"));
            nameText.setText(receivedIntent.getStringExtra("name"));
            String cat = receivedIntent.getStringExtra("category");
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(cat)){
                    categorySpinner.setSelection(i);
                    break;
                }
            }
            ratingBar.setRating(receivedIntent.getFloatExtra("rate", 0));
            releaseDateText.setText(receivedIntent.getStringExtra("release_date"));
            descriptionMultiLine.setText(receivedIntent.getStringExtra("description"));
            reviewMultiLines.setText(receivedIntent.getStringExtra("review"));

            editData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View V) {
                    Intent receivedIntent = getIntent();
                    int id = receivedIntent.getIntExtra("id",-1);
                    String rateDate = receivedIntent.getStringExtra("rate_date");
                    String name = nameText.getText().toString();


                    CultureText cultureText = new CultureText(name, ratingBar.getRating(), categorySpinner.getSelectedItem().toString(),
                            releaseDateText.getText().toString(), rateDate, descriptionMultiLine.getText().toString(), reviewMultiLines.getText().toString());
                    if (!nameText.getText().toString().equals("") && !releaseDateText.getText().toString().equals("") && !releaseDateText.getText().toString().equals("Release Date")) {
                        database.updateData(cultureText, id);

                        Toast.makeText(AddCultureText.this, "Data edited", Toast.LENGTH_SHORT).show();

                        Intent goBackIntent = new Intent(AddCultureText.this, DatabaseViewActivity.class);
                        startActivity(goBackIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(AddCultureText.this, "Data unable to edit", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            deleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View V) {
                    Intent receivedIntent = getIntent();
                    int id = receivedIntent.getIntExtra("id",-1);
                    if(database.deleteData(id)) {
                        Toast.makeText(AddCultureText.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddCultureText.this, "Unable to delete", Toast.LENGTH_SHORT).show();
                    }
                    Intent goBackIntent = new Intent(AddCultureText.this, DatabaseViewActivity.class);
                    startActivity(goBackIntent);
                    finish();
                }
            });
        }

        /* IF ADD CULTURE TEXT ACTIVITY */
        else {
            viewData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddCultureText.this, DatabaseViewActivity.class);
                    startActivity(intent);
                }
            });

            addData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View V) {

                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                    String rateDate = year + "/" + (month+1) + "/" + day;

                    CultureText cultureText = new CultureText(nameText.getText().toString(), ratingBar.getRating(), categorySpinner.getSelectedItem().toString(),
                            releaseDateText.getText().toString(), rateDate, descriptionMultiLine.getText().toString(), reviewMultiLines.getText().toString());

                    if (!nameText.getText().toString().equals("") && !releaseDateText.getText().toString().equals("") && !releaseDateText.getText().toString().equals("Release Date") && database.insertData(cultureText))
                    {
                        Toast.makeText(AddCultureText.this, "Data added", Toast.LENGTH_SHORT).show();

                        nameText.setText("");
                        ratingBar.setRating(0);
                        descriptionMultiLine.setText("");
                        reviewMultiLines.setText("");
                        releaseDateText.setText("Release Date");
                    }
                    else
                        Toast.makeText(AddCultureText.this, "Data not added", Toast.LENGTH_SHORT).show();
                }
            });
        }



        choseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(AddCultureText.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        releaseDateText.setText(mYear + "/" + (mMonth+1) + "/" + mDay);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}
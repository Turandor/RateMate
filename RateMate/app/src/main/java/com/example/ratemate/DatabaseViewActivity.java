package com.example.ratemate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;


public class DatabaseViewActivity extends AppCompatActivity{

    DatabaseHelper db;
    ListView cultureTextsList;
    Spinner categorySpinner;
    Spinner sortSpinner;
    ImageView upDownButton;


    ArrayAdapter adapter;
    ArrayList<String> listItem;

    String[] categories = { "All", "Film", "TV Series", "Book", "Comic book", "Computer Game", "Music Album", "Theater performance", "Poem" };
    String[] sorting = { "Rate date", "Alphabetically", "Rate", "Release date" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_view);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        cultureTextsList = findViewById(R.id.users_list);
        categorySpinner = findViewById(R.id.categorySpinner);
        sortSpinner = findViewById(R.id.sortSpinner);
        upDownButton = findViewById(R.id.upDownButton);

        upDownButton.setTag("DESC");
        //db.clearDatabase();

        upDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if(upDownButton.getTag() == "DESC" ) {
                    upDownButton.setImageResource(R.drawable.ic_up);
                    upDownButton.setTag("ASC");
                    listItem.clear();
                    viewData(categorySpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString(),upDownButton.getTag().toString());
                }
                else {
                    upDownButton.setImageResource(R.drawable.ic_down);
                    upDownButton.setTag("DESC");
                    listItem.clear();
                    viewData(categorySpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString(),upDownButton.getTag().toString());
                }


            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                cultureTextsList.setAdapter(null);
                listItem.clear();
                viewData(categorySpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString(),upDownButton.getTag().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, sorting);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        sortSpinner.setAdapter(adapter1);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                cultureTextsList.setAdapter(null);
                listItem.clear();
                viewData(categorySpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString(),upDownButton.getTag().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void viewData(String category, String sort, String upDownSort) {

        sort = DBSortAlias(sort);
        String[] shortDate;
        String rate;
        Cursor cursor;

        if (category == "All")
            cursor = db.viewData(sort, upDownSort);
        else
            cursor = db.viewData(category, sort, upDownSort);

        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data to show", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()){
                shortDate = cursor.getString(4).split("/");
                rate = String.valueOf(cursor.getFloat(2));
                if(rate.endsWith(".0"))
                    rate = rate.substring(0,1);

                listItem.add((cursor.getString(1) +
                        " (" + shortDate[0] +") "+
                        rate+"/5 \n"+
                        cursor.getString(3)));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            cultureTextsList.setAdapter(adapter);
            cultureTextsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString().split(" \\(")[0]; //first part of the string

                    Log.d("DatabaseViewActivity", "onItemClick: You Clicked on " + name);

                    Cursor cursor = db.getItemID(name);
                    int itemID = -1;
                    float rate = 0;
                    String category = "";
                    String release_date= "";
                    String rate_date= "";
                    String description= "";
                    String review= "";

                    while (cursor.moveToNext()){
                        itemID = cursor.getInt(0);
                        //name column 1 from search
                        rate = cursor.getFloat(2);
                        category = cursor.getString(3);
                        release_date = cursor.getString(4);
                        rate_date = cursor.getString(5);
                        description = cursor.getString(6);
                        review = cursor.getString(7);
                    }
                    if(itemID > -1){
                        Intent editScreenIntent = new Intent(DatabaseViewActivity.this, AddCultureText.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("name", name);
                        editScreenIntent.putExtra("rate", rate);
                        editScreenIntent.putExtra("category", category);
                        editScreenIntent.putExtra("rate_date", rate_date);
                        editScreenIntent.putExtra("release_date", release_date);
                        editScreenIntent.putExtra("description", description);
                        editScreenIntent.putExtra("review", review);
                        startActivity(editScreenIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(DatabaseViewActivity.this, "No ID associated with that name", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> cultureTextsArray = new ArrayList<>();

                for (String name : listItem) {
                    if (name.toLowerCase().contains(newText.toLowerCase())){
                        cultureTextsArray.add(name);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DatabaseViewActivity.this,
                        android.R.layout.simple_list_item_1, cultureTextsArray);
                cultureTextsList.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public String DBSortAlias (String sort) {
        switch (sort) {
            case "Alphabetically":
                return "NAME";
            case "Rate":
                return "RATE";
            case "Release date":
                return "RELEASE_DATE";
            case "Rate date":
                return "RATE_DATE";
            default:
                return "";
        }
    }
}

package com.example.ratemate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB3.db";
    private static final String DB_TABLE = "CultureText";

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String RATE = "RATE";
    private static final String CATEGORY = "CATEGORY";
    private static final String RELEASE_DATE = "RELEASE_DATE";
    private static final String RATE_DATE = "RATE_DATE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String REVIEW = "REVIEW";

    private static final String CREATE_TABLE = "CREATE TABLE "+ DB_TABLE+" ("+
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+ " TEXT NOT NULL, "+
            RATE+ " FLOAT, "+
            CATEGORY+ " TEXT, "+
            RELEASE_DATE+ " TEXT, "+
            RATE_DATE+ " TEXT, "+
            DESCRIPTION+ " TEXT, "+
            REVIEW+ " TEXT"+")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
        onCreate(db);
    }

    public boolean insertData(CultureText cultureText){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, cultureText.name);
        contentValues.put(RATE, cultureText.rate);
        contentValues.put(CATEGORY, cultureText.category);
        contentValues.put(RELEASE_DATE, cultureText.release_date);
        contentValues.put(RATE_DATE, cultureText.rate_date);
        contentValues.put(DESCRIPTION, cultureText.description);
        contentValues.put(REVIEW, cultureText.review);

        long results = db.insert(DB_TABLE,null,contentValues);

        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+DB_TABLE;
        db.execSQL(clearDBQuery);
    }
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Cursor viewData(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE + " WHERE CATEGORY=" + "'"+category+"'";
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Cursor viewData(String sorting, String upDownSort) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE + " ORDER BY " + sorting + " " +upDownSort;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }
    public Cursor viewData(String category, String sorting, String upDownSort) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE + " WHERE CATEGORY=" + "'"+category+"'" + " ORDER BY " + sorting + " " +upDownSort;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *" + " FROM " + DB_TABLE + " WHERE " + NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public void updateData(CultureText cultureText, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, cultureText.name);
        contentValues.put(RATE, cultureText.rate);
        contentValues.put(CATEGORY, cultureText.category);
        contentValues.put(RELEASE_DATE, cultureText.release_date);
        contentValues.put(RATE_DATE, cultureText.rate_date);
        contentValues.put(DESCRIPTION, cultureText.description);
        contentValues.put(REVIEW, cultureText.review);

        db.update(DB_TABLE, contentValues, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(DB_TABLE, ID + "=" + id, null) > 0;
    }
}

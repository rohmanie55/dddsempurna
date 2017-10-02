package com.mr.rohmani.diadiasempurna;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mr.rohmani.diadiasempurna.model.modelBookmark;
import com.mr.rohmani.diadiasempurna.model.storyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 31/07/2017.
 */

public class dbHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "ddds_story";
    protected static final String TABLE_NAME = "story";
    protected static final String TABLE_NAME2 = "bookmark";
    protected static final String COLUMN_TITLE = "title";
    protected static final String COLUMN_BODY = "body";
    protected static final String COLUMN_KEY = "part";
    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); //untuk delete database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_KEY + " INTEGER PRIMARY KEY," + COLUMN_TITLE + " TEXT,"
                + COLUMN_BODY + " TEXT" + ")";
        String CREATE_USER_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_KEY + " INTEGER," + COLUMN_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + "";
        String sql2 = "DROP TABLE IF EXISTS " + TABLE_NAME2 + "";
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);
    }

    // FUNGSI UNTUK AMBIL 1 DATA Story
    public storyModel getStory(int key){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_KEY, COLUMN_TITLE, COLUMN_BODY},
                COLUMN_KEY + "=?", new String[]{String.valueOf(key)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        storyModel story = new storyModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return story;
    }

    //fungsi mengambil semua story
    public List<storyModel> getAllStory(){
        List<storyModel> storyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME +" ORDER BY "+COLUMN_KEY+" DESC" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                storyModel story = new storyModel(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
                storyList.add(story);
            } while (cursor.moveToNext());
        }
        return storyList;
    }

    public List<modelBookmark> getAllBookmark(){
        List<modelBookmark> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME2 +" ORDER BY "+COLUMN_KEY+" DESC" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                modelBookmark book = new modelBookmark(cursor.getInt(1),cursor.getString(2));
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        return bookList;
    }


    public void insertBookmark(int page, String title){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_KEY, page);
        values.put(COLUMN_TITLE, title);

        db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    public void deleteBookmark(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, COLUMN_KEY + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


}

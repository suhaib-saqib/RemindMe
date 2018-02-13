package com.example.nngo1.remindme.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nngo1 on 15-Oct-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "remindme.db";
    public static final int DATABASE_VERSION = 1;
    public static final String RELATIONS_TABLE = "relations";
    public static final String EVENTS_TABLE = "events";
    public static final String TASKS_TABLE = "tasks";
    public static final String ACCOUNTS_TABLE = "accounts";
    public static DatabaseHelper instance = null;
    // private static DatabaseReference mDatabase;

    // Singleton pattern, enforces only one copy of the databasehelper
    // by storing a reference to itself, because SQLite is just a DB wrapper
    // around a file, so SQLite doesn't handle multithread
    public static DatabaseHelper getInstance(Context context) {
    //  run deleteDatabase line once to  delete database then comment it out again
    // should you need to reset the db
       context.deleteDatabase(DATABASE_NAME);
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;


    }

    // force usage of getinstance and hide constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String createQuery = "CREATE TABLE " + RELATIONS_TABLE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL, " +
            "phone TEXT, " +
            "address TEXT, " +
            "relationship TEXT)";
        String createEventQuery = "CREATE TABLE " + EVENTS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL, " +
                "date TEXT)";

        String createTaskQuery = "CREATE TABLE " + TASKS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT NOT NULL, " +
                "completed INTEGER NOT NULL DEFAULT 0, " +
                "dateAdded DATE)";
        String createAccountQuery = "CREATE TABLE " + ACCOUNTS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL, " +
                "role TEXT)";
        String account1 = "INSERT INTO " + ACCOUNTS_TABLE +
                " VALUES (1, \"Dr. Lovrick\", \"Doctor\")";
        String account2 = "INSERT INTO " + ACCOUNTS_TABLE +
                " VALUES (2, \"Nathaniel Ngo\", \"Patient\")";
        String account3 = "INSERT INTO " + ACCOUNTS_TABLE +
                " VALUES (3, \"Suhaib Saqib\", \"Caretaker\")";
        db.execSQL(createQuery);
        db.execSQL(createEventQuery);
        db.execSQL(createTaskQuery);
        db.execSQL(createAccountQuery);
        db.execSQL(account1);
        db.execSQL(account2);
        db.execSQL(account3);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

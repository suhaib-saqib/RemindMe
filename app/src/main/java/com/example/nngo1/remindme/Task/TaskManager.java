package com.example.nngo1.remindme.Task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nngo1.remindme.Database.DatabaseHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by nngo1 on 09-Jul-17.
 */

public class TaskManager {


    private DatabaseHelper dbHelper;

    public TaskManager(Context context) {
        // set up as a singleton
        dbHelper = DatabaseHelper.getInstance(context);
    }
    public List<Task> getList() {
        /*SQLiteDatabase db = dbHelper.getReadableDatabase();

        // move through returned row one at a time
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TASKS_TABLE,
                null
        );

        List<Task> tasks = new ArrayList<>();
        // walk through cursor and add into a list
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Task task = new Task(
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getInt(cursor.getColumnIndex("completed")) != 0,
                        cursor.getLong(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("dateAdded"))
                );
                tasks.add(task);
                cursor.moveToNext();
            }
        }
        cursor.close();*/
        return null;
    }

    public void addTask(Task task) {
        ContentValues newTask = new ContentValues();
        // column name as keys
        newTask.put("description", task.getDescription());
        newTask.put("completed", task.isComplete());
        newTask.put("dateAdded", task.getDateAdded());

        // write lock to SQLite db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // nullColumnHack, SQLite doesn't handle passing in nothing for an update or insert
        // pass in null to pass in nothing
        db.insert(DatabaseHelper.TASKS_TABLE, null, newTask);
        // db.close(), not used b/c singleton, leave open, when application is closed, it will close
    }

    public void deleteTask(int index) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TASKS_TABLE, "_id=\"" + index + "\"", null);
    }

    public void updateTask(Task task) {
        ContentValues editTask = new ContentValues();
        editTask.put("description", task.getDescription());
        editTask.put("completed", task.isComplete());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[] { String.valueOf(task.getId()) };

        db.update(DatabaseHelper.TASKS_TABLE, editTask, "_id=?", args);
    }

}

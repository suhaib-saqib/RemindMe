package com.example.nngo1.remindme.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nngo1.remindme.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nngo1 on 26-Nov-17.
 */

public class EventScheduler {
    private DatabaseHelper dbHelper;

    public EventScheduler(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<Event> getList() {
        /*SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.EVENTS_TABLE,
                null);
        List<Event> events = new ArrayList<>();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Event event = new Event(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getLong(cursor.getColumnIndex("_id"))
                );
                events.add(event);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return events;
    */
        return null;
    }

    public void addEvent(Event event) {
        ContentValues newEvent = new ContentValues();
        newEvent.put("name", event.getName());
        newEvent.put("date", event.getDate());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DatabaseHelper.EVENTS_TABLE, null, newEvent);
    }

    public void deleteEvent(String index) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.EVENTS_TABLE, "_id=\"" + index + "\"", null);
    }

    public void updateEvent(Event event) {
        ContentValues editEvent = new ContentValues();
        editEvent.put("name", event.getName());
        editEvent.put("date", event.getDate());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[] {
                String.valueOf(event.getId())
        };
        db.update(DatabaseHelper.EVENTS_TABLE, editEvent, "_id=?", args);
    }
}

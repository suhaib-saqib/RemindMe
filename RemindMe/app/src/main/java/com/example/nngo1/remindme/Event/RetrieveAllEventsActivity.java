package com.example.nngo1.remindme.Event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nngo1.remindme.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAllEventsActivity extends AppCompatActivity {
    private EventScheduler listManager;
    private EventAdapter adapter;
    private DatabaseReference mDatabase;
    private ArrayList<Event> events;

    private void onAddEventButtonClick() {
        Intent eventIntent = new Intent(this, CreateEventActivity.class);
        startActivityForResult(eventIntent, 1);
    }

    private void onUpdateEventButtonClick(Context context, Event event) {
        Intent eventIntent = new Intent(this, UpdateEventActivity.class);
        eventIntent.putExtra("event", event);
        startActivityForResult(eventIntent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_all_events);

        ImageButton addEvent = (ImageButton) findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddEventButtonClick();
            }
        });
        ListView eventsList = (ListView) findViewById(R.id.event_list);
        events = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("events");


        listManager = new EventScheduler(getApplicationContext());
        adapter = new EventAdapter(
                this,
                events
        );

        eventsList.setAdapter(adapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                adapter.add(event);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private class EventAdapter extends ArrayAdapter<Event> {
        private Context context;
        private List<Event> events;
        private LayoutInflater inflater;

        public EventAdapter(
                Context context,
                List<Event> events
        ) {
            super(context, -1, events);
            this.context = context;
            this.events = events;
            this.inflater = LayoutInflater.from(context);
        }

        public void swapEvents(List<Event> events) {
            this.events = events;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EventViewHolder holder;

            if(convertView == null) {
                inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.event_layout, parent, false);
                holder = new EventViewHolder();
                holder.eventName = (TextView) convertView.findViewById(R.id.list_event_name);
                holder.eventDate = (TextView) convertView.findViewById(R.id.list_event_date);
                holder.eventTime = (TextView) convertView.findViewById(R.id.list_event_time);
                holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_event);
                holder.deleteButton.setOnClickListener(new DeleteOnClickListener(getApplicationContext(), position));
                convertView.setTag(holder);
            } else {
                holder = (EventViewHolder) convertView.getTag();
            }

            holder.eventName.setText(events.get(position).getName());
            holder.eventDate.setText(events.get(position).getDate());
            holder.eventTime.setText(events.get(position).getTime());

            convertView.setOnClickListener(new UpdateOnClickListener(getApplicationContext(), events.get(position)));
            return convertView;
        }

        class DeleteOnClickListener implements View.OnClickListener {
            private int index;
            private Context context;

            public DeleteOnClickListener(Context context, int index) {
                this.context = context;
                this.index = index;
            }

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RetrieveAllEventsActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(String.valueOf(events.get(index).getId())).child("id").removeValue();
                                mDatabase.child(String.valueOf(events.get(index).getId())).child("name").removeValue();
                                mDatabase.child(String.valueOf(events.get(index).getId())).child("date").removeValue();

                                mDatabase.child(String.valueOf(events.get(index).getId())).child("time").removeValue();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        }
    }

    class UpdateOnClickListener implements View.OnClickListener {
        private Event event;
        private Context context;

        public UpdateOnClickListener(Context context, Event event) {
            this.context = context;
            this.event = event;
        }

        @Override
        public void onClick(View view) {
            mDatabase.child(String.valueOf(event.getId())).child("id").removeValue();
            mDatabase.child(String.valueOf(event.getId())).child("name").removeValue();
            mDatabase.child(String.valueOf(event.getId())).child("date").removeValue();
            mDatabase.child(String.valueOf(event.getId())).child("time").removeValue();
            onUpdateEventButtonClick(context, event);
        }
    }

    public static class EventViewHolder {
        private TextView eventName;
        private TextView eventDate;
        private ImageButton deleteButton;
        private TextView eventTime;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Event event = data.getParcelableExtra("event");
 //               listManager.addEvent(event);
//                adapter.swapEvents(listManager.getList());
                mDatabase
                        .child(String.valueOf(event.getId())).child("id").setValue(String.valueOf(event.getId()));
                mDatabase.child(String.valueOf(event.getId())).child("name").setValue(event.getName());
                mDatabase.child(String.valueOf(event.getId())).child("date").setValue(event.getDate());
                mDatabase.child(String.valueOf(event.getId())).child("time").setValue(event.getTime());
                Toast.makeText(this, "Added " + event.getName() +
                    " to list of events",
                    Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                Event event = data.getParcelableExtra("event");
 //               listManager.updateEvent(event);
 //               adapter.swapEvents(listManager.getList());
                mDatabase
                        .child(String.valueOf(event.getId())).child("id").setValue(String.valueOf(event.getId()));
                mDatabase.child(String.valueOf(event.getId())).child("name").setValue(event.getName());
                mDatabase.child(String.valueOf(event.getId())).child("date").setValue(event.getDate());
                mDatabase.child(String.valueOf(event.getId())).child("time").setValue(event.getTime());
                Toast.makeText(this, "Updated " + event.getName() +
                    " in list of events",
                    Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("event", "error on activity result");
        }
    }
}

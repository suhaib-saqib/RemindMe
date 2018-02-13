package com.example.nngo1.remindme.Event;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nngo1.remindme.R;


/**
 * Created by nngo1 on 26-Nov-17.
 */

public class UpdateEventActivity extends AppCompatActivity {
    private EventScheduler eventScheduler;
    private Intent result;
    EditText name, date, time;
    Event event;

    private void onSubmitButtonClick() {
        result = new Intent();
        // check if the DB id gets copied into here, bc the ID is the
        // basis on which deletion and editing resolves which
        // record to delete, edit

        Event editEvent = new Event(
            name.getText().toString(),
            date.getText().toString(),
            String.valueOf(event.getId())
        );
        result.putExtra("event", editEvent);
        setResult(AppCompatActivity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        eventScheduler = new EventScheduler(getApplicationContext());
        name = (EditText) findViewById(R.id.event_name);
        date = (EditText) findViewById(R.id.event_date_label);
        time = (EditText) findViewById(R.id.event_time_label);
        event = getIntent().getParcelableExtra("event");
        name.setText(event.getName());
        date.setText(event.getDate());
        time.setText(event.getTime());

        Button submitButton = (Button) findViewById(R.id.submit_event);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }
}

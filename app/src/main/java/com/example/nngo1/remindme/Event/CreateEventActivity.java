package com.example.nngo1.remindme.Event;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.nngo1.remindme.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {
    private EventScheduler eventScheduler;
    private EditText name;
    private String date;
    private String date1;
    private Time time1;
    private EditText time;
    private Calendar calendar;
    private Event event;

    public void scheduleNotification(Context context, long delay, int notificationID) {
        String CHANNEL_ID = "roana_channel";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(name.getText())
            .setContentText("text")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.email);
        Intent intent = new Intent(context, RetrieveAllEventsActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, CustomBroadcastReceiver.class);
        notificationIntent.putExtra(CustomBroadcastReceiver.NOTIFICATION_ID, notificationID);
        notificationIntent.putExtra(CustomBroadcastReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMills = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMills, pendingIntent);
    }

    private void onSubmitButtonClick() {
        Intent result = new Intent();
        /*PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), result, 0);

        Notification n = new Notification.Builder(this)
                .setContentTitle(name.getText().toString())
                //.setContentText("Subject")
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.email)
                .setAutoCancel(true)
                .setSmallIcon(null)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
*/

        event = new Event(
                name.getText().toString(),
                date1.toString(),
                time.getText().toString()
        );


//        scheduleNotification(this, date1.getTime() - new Date().getTime(), 1);
        scheduleNotification(this, 0, 1);

        System.out.println("ONSUBMIT, time: " + time);
        result.putExtra("event", event);
        setResult(AppCompatActivity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        CalendarView eventDate = (CalendarView) findViewById(R.id.event_date);
        name = (EditText) findViewById(R.id.event_name);
        calendar = Calendar.getInstance();
        final EditText eventDateLabel = (EditText) findViewById(R.id.event_date_label);

        eventDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    @NonNull CalendarView calendarView,
                    int year,
                    int month,
                    int day) {
                String [] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
                eventDateLabel.setText(String.valueOf(year + " " + months[month] + " " + day));
                date = String.valueOf(year + " " + months[month] + " " + day);
                DateFormat df = new SimpleDateFormat("yyyy MM dd");
                String monthNumber = String.format("%02d", (month + 1));
                String dayNumber = String.format("%02d", day);
                date1 = year + "/" + monthNumber + "/" + dayNumber;

            }
        });
        time = findViewById(R.id.event_time_label);
        time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(CreateEventActivity.this, new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(String.format("%02d:%02d", view.getHour(), view.getMinute()));
                    }
                }, hour, minute, true);
                timePicker.setTitle("Select time");
                timePicker.show();

            }
        });


        Button submitButton = (Button) findViewById(R.id.submit_event);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }
}

package com.example.nngo1.remindme.Relation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nngo1.remindme.Account.RetrieveAllAccountsActivity;
import com.example.nngo1.remindme.Event.RetrieveAllEventsActivity;
import com.example.nngo1.remindme.R;
import com.example.nngo1.remindme.Task.RetrieveAllTasksActivity;

public class MainActivity extends AppCompatActivity {

    private void onRetrieveEventsButtonClick() {
        Intent eventIntent = new Intent(this, RetrieveAllEventsActivity.class);
        startActivity(eventIntent);
    }

    private void onRetrieveRelationsButtonClick() {
        Intent relationsIntent = new Intent(this, RetrieveAllRelationsActivity.class);
        startActivity(relationsIntent);
    }

    private void onRetrieveTasksButtonClick() {
        Intent tasksIntent = new Intent(this, RetrieveAllTasksActivity.class);
        startActivity(tasksIntent);
    }

    private void onRetrieveAccountsButtonClick() {
        Intent accountsIntent = new Intent(this, RetrieveAllAccountsActivity.class);
        startActivity(accountsIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button viewEvent = (Button) findViewById(R.id.view_events);
        Button viewRelations = (Button) findViewById(R.id.view_relations);
        Button viewTasks = (Button) findViewById(R.id.view_tasks);
        Button viewAccounts = (Button) findViewById(R.id.view_accounts);
        viewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieveEventsButtonClick();
            }
        });

        viewRelations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieveRelationsButtonClick();
            }
        });
        viewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieveTasksButtonClick();
            }
        });
        viewAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onRetrieveAccountsButtonClick();
            }
        });
    }




}

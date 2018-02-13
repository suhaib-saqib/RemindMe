package com.example.nngo1.remindme.Task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.nngo1.remindme.R;

public class UpdateTaskActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private Intent intent;
    private EditText description;
    private CheckBox isComplete;
    private Task task;


    private void onSubmitButtonClick() {
        intent = new Intent();
        Task editTask = new Task(description.getText().toString(), task.isComplete(), task.getId());
        intent.putExtra("task", editTask);
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskManager = new TaskManager(getApplicationContext());
        description = (EditText) findViewById(R.id.description);
        task = getIntent().getParcelableExtra("task");

        description.setText(task.getDescription());
        Button submitButton = (Button) findViewById(R.id.submit_task);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }
}

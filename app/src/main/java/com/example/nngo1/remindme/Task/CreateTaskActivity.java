package com.example.nngo1.remindme.Task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nngo1.remindme.R;

public class CreateTaskActivity extends AppCompatActivity {
    private Intent intent;
    private EditText description;

    private void onSubmitButtonClick() {
        intent = new Intent();
        Task task = new Task(
                description.getText().toString(),
                false
        );
        intent.putExtra("task", task);
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        description = (EditText) findViewById(R.id.description);
        Button submitButton = (Button) findViewById(R.id.submit_task);

        //lambda expressions are not supported at this language level
        //submitButton.setOnClickListener((view) -> {onSubmitButtonClick();});

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }
}

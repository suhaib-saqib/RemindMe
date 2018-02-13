package com.example.nngo1.remindme.Relation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nngo1.remindme.R;

public class CreateRelationActivity extends AppCompatActivity {
    private RelationManager relationManager;
    private Intent result;
    EditText name, phone, address, relationship;
    private void onSubmitButtonClick() {

        // Retrieves data from form fields
        result = new Intent();
        Relation relation = new Relation(
            name.getText().toString(),
            phone.getText().toString(),
            address.getText().toString(),
            relationship.getText().toString()
        );
        result.putExtra("relation", relation);
        setResult(AppCompatActivity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_relation);
        relationManager = new RelationManager(getApplicationContext());
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        relationship = (EditText) findViewById(R.id.relationship);
        Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick();
            }
        });
    }


}

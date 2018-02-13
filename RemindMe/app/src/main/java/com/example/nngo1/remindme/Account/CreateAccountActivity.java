package com.example.nngo1.remindme.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nngo1.remindme.R;


public class CreateAccountActivity extends AppCompatActivity {
    private TextView accountName, accountRole;

    private void onSubmitButtonClick() {
        Account account = new Account(
            accountName.getText().toString(),
            accountRole.getText().toString()
        );
        Intent accountIntent = new Intent();
        accountIntent.putExtra("account", account);
        setResult(AppCompatActivity.RESULT_OK, accountIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        accountName = findViewById(R.id.createaccount_name);
        accountRole = findViewById(R.id.createaccount_role);
        Button submitAccount = findViewById(R.id.submit_account);

        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });
    }
}

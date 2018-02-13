package com.example.nngo1.remindme.Account;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.nngo1.remindme.R;

public class UpdateAccountActivity extends AppCompatActivity {
    private Intent result;
    private EditText name, role;
    private Account account;
    private Button submitButton;


    private void onSubmitButtonClick() {
        result = new Intent();
        Account editAccount = new Account(
                name.getText().toString(),
                role.getText().toString()
        );
        result.putExtra("account", editAccount);
        setResult(AppCompatActivity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name = (EditText) findViewById(R.id.createaccount_name);
        role = (EditText) findViewById(R.id.createaccount_role);
        account = getIntent().getParcelableExtra("account");
        submitButton = (Button) findViewById(R.id.submit_account);

        name.setText(account.getName());
        role.setText(account.getRole());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });
    }
}

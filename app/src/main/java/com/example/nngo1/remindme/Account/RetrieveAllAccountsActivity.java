package com.example.nngo1.remindme.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nngo1.remindme.Event.Event;
import com.example.nngo1.remindme.Event.RetrieveAllEventsActivity;
import com.example.nngo1.remindme.R;
import com.example.nngo1.remindme.Relation.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAllAccountsActivity extends AppCompatActivity {
    //private AccountManager listManager;
    private AccountAdapter adapter;
    private DatabaseReference mDatabase;
    private ArrayList<Account> accounts;

    private void onAddAccountButtonClick() {
        Intent accountIntent = new Intent(this, CreateAccountActivity.class);
        startActivityForResult(accountIntent, 1);
    }

    private void onUpdateAccountButtonClick(Context context, Account account) {
        Intent accountIntent = new Intent(this, UpdateAccountActivity.class);
        accountIntent.putExtra("account", account);
        startActivityForResult(accountIntent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_all_accounts);
        mDatabase = FirebaseDatabase.getInstance().getReference("accounts");
        ListView accountsList = (ListView) findViewById(R.id.account_list);
        //listManager = new AccountManager(getApplicationContext());
        accounts = new ArrayList<>();
        ImageView addAccountButton = (ImageView) findViewById(R.id.add_account);
        adapter = new AccountAdapter(
                this,
                accounts
        );
        accountsList.setAdapter(adapter);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddAccountButtonClick();
            }
        });
        /*adapter = new AccountAdapter(
                this,
                listManager.getList()
        );*/


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Account account = dataSnapshot.getValue(Account.class);
                adapter.add(account);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Account account = data.getParcelableExtra("account");
                //listManager.addAccount(event);
   //             adapter.swapAccounts(listManager.getList());
                mDatabase.child(String.valueOf(account.getId())).child("id").setValue(String.valueOf(account.getId()));
                mDatabase.child(String.valueOf(account.getId())).child("name").setValue(account.getName());
                mDatabase.child(String.valueOf(account.getId())).child("role").setValue(account.getRole());
                Toast.makeText(this, "Added " + account.getName() +
                                " to list of accounts",
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                Account account = data.getParcelableExtra("account");
                //listManager.updateAccount(event);
 //               adapter.swapAccounts(listManager.getList());
                mDatabase.child(String.valueOf(account.getId())).child("id").setValue(String.valueOf(account.getId()));
                mDatabase.child(String.valueOf(account.getId())).child("name").setValue(account.getName());
                mDatabase.child(String.valueOf(account.getId())).child("role").setValue(account.getRole());
                Toast.makeText(this, "Updated " + account.getName() +
                                " in list of accounts",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("account", "error on activity result");
        }
    }

    private class AccountAdapter extends ArrayAdapter<Account> {
        private Context context;
        private List<Account> accounts;
        private LayoutInflater inflater;
        public AccountAdapter(
                Context context,
                List<Account> accounts
        ) {
            super(context, -1, accounts);
            this.context = context;
            this.accounts = accounts;
            this.inflater = LayoutInflater.from(context);
        }

        /*public void swapAccounts(List<Account> accounts) {
            this.accounts = accounts;
            notifyDataSetChanged();
        }*/

        @Override
        public int getCount() {
            return accounts.size();
        }

        /*@Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AccountViewHolder holder;

            if (convertView == null) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.account_layout, parent, false);

                holder = new AccountViewHolder();
                System.out.println("ACCOUNT VIEW HOLDER" + holder.toString());
                holder.accountName = (TextView) convertView.findViewById((R.id.viewaccount_name));
                holder.accountRole = (TextView) convertView.findViewById((R.id.viewaccount_role));
            } else {
                holder = (AccountViewHolder) convertView.getTag();
            }
            holder.accountName.setText(accounts.get(position).getName());
            holder.accountRole.setText(accounts.get(position).getRole());

            convertView.setOnClickListener(new UpdateOnClickListener(getApplicationContext(), accounts.get(position)));


            return convertView;
        }*/

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AccountViewHolder holder;

            if(convertView == null) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.account_layout, parent, false);

                holder = new AccountViewHolder();
                holder.accountName = (TextView) convertView.findViewById(R.id.viewaccount_name);
                holder.accountRole = (TextView) convertView. findViewById(R.id.viewaccount_role);
                holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_account);
                holder.deleteButton.setOnClickListener(new DeleteOnClickListener(getApplicationContext(), position));
                // forgot what this does
                convertView.setTag(holder);
            } else {
                // and this
                holder = (AccountViewHolder) convertView.getTag();
            }

            // what were the tags for?

            holder.accountName.setText(accounts.get(position).getName());
            holder.accountRole.setText(accounts.get(position).getRole());


            // adds a click event handler on the row itself
            convertView.setOnClickListener(new UpdateOnClickListener(getApplicationContext(), accounts.get(position)));

            // holder.setTag(relations.get(position));

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
                new AlertDialog.Builder(RetrieveAllAccountsActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(String.valueOf(accounts.get(index).getId())).child("id").removeValue();
                                mDatabase.child(String.valueOf(accounts.get(index).getId())).child("name").removeValue();
                                mDatabase.child(String.valueOf(accounts.get(index).getId())).child("role").removeValue();                               notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        }

    }

    class UpdateOnClickListener implements View.OnClickListener {
        private Account account;
        private Context context;

        public UpdateOnClickListener(Context context, Account account) {
            this.context = context;
            this.account = account;
        }

        @Override
        public void onClick(View view) {
            mDatabase.child(String.valueOf(account.getId())).child("id").removeValue();
            mDatabase.child(String.valueOf(account.getId())).child("name").removeValue();
            mDatabase.child(String.valueOf(account.getId())).child("role").removeValue();
            onUpdateAccountButtonClick(context, account);
        }
    }

    public static class AccountViewHolder {
        private TextView accountName;
        private TextView accountRole;
        private ImageButton deleteButton;
    }
}

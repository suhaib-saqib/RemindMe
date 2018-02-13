package com.example.nngo1.remindme.Relation;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nngo1.remindme.Account.RetrieveAllAccountsActivity;
import com.example.nngo1.remindme.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAllRelationsActivity extends AppCompatActivity {
    //private RelationManager listManager;
    private RelationAdapter adapter;
    private ArrayList<Relation> relations;
    private DatabaseReference mDatabase;
    private void onAddRelationButtonClick() {
        Intent relationIntent = new Intent(this, CreateRelationActivity.class);
        startActivityForResult(relationIntent, 1);
    }

    private void onUpdateRelationButtonClick(Context context, Relation relation) {
        Intent relationIntent = new Intent(this, UpdateRelationActivity.class);
        relationIntent.putExtra("relation", relation);
        // put relation int he intent
        startActivityForResult(relationIntent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_all_relations);

        mDatabase = FirebaseDatabase.getInstance().getReference("relations");
        ImageButton addRelation = (ImageButton) findViewById(R.id.add_relation);

        addRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddRelationButtonClick();
            }
        });

        ListView relationsList = (ListView) findViewById(R.id.relation_list);
        relations = new ArrayList<>();

//        listManager = new RelationManager(getApplicationContext());

        adapter = new RelationAdapter(
                this,
                relations
        );

        relationsList.setAdapter(adapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Relation relation = dataSnapshot.getValue(Relation.class);
                adapter.add(relation);
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


    // called when application is moved to background, alt tabbed on phone
    // save list on alt tab
    @Override
    protected void onPause() {
        super.onPause();
    }

    private class RelationAdapter extends ArrayAdapter<Relation> {
        private Context context;
        private List<Relation> relations;
        private LayoutInflater inflater;
        public RelationAdapter(
                Context context,
                List<Relation> relations
        ) {
            super(context, -1, relations);
            this.context = context;
            this.relations = relations;
            this.inflater = LayoutInflater.from(context);
        }

        // updates the view when a change happens, eg. new, update, delete
        public void swapRelations(List<Relation> relations) {
            this.relations = relations;
            notifyDataSetChanged();
        }

        // tells listview how many items to render
        @Override
        public int getCount() {
            return relations.size();
        }


        // implement viewholder pattern, improve performance of recycling existing views
        // in listview
        // defines how each item in the listview is rendered
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RelationViewHolder holder;

            if(convertView == null) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.relation_layout, parent, false);

                holder = new RelationViewHolder();
                holder.relationName = (TextView) convertView.findViewById(R.id.relation_name);
                holder.relationPhone = (TextView) convertView. findViewById(R.id.relation_phone);
                holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_relation);
                holder.deleteButton.setOnClickListener(new DeleteOnClickListener(getApplicationContext(), position));

                // forgot what this does
                convertView.setTag(holder);
            } else {
                // and this
                holder = (RelationViewHolder) convertView.getTag();
            }

            // what were the tags for?

            holder.relationName.setText(relations.get(position).getName());
            holder.relationPhone.setText(relations.get(position).getPhone());

            // adds a click event handler on the row itself
            convertView.setOnClickListener(new UpdateOnClickListener(getApplicationContext(), relations.get(position)));

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
                new AlertDialog.Builder(RetrieveAllRelationsActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(String.valueOf(relations.get(index).getId())).child("id").removeValue();
                                mDatabase.child(String.valueOf(relations.get(index).getId())).child("name").removeValue();
                                mDatabase.child(String.valueOf(relations.get(index).getId())).child("phone").removeValue();
                                mDatabase.child(String.valueOf(relations.get(index).getId())).child("address").removeValue();
                                mDatabase.child(String.valueOf(relations.get(index).getId())).child("relationship").removeValue();
                           }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        }

        class UpdateOnClickListener implements View.OnClickListener {
            private Relation relation;
            private Context context;


            public UpdateOnClickListener(Context context, Relation relation) {
                this.context = context;
                this.relation = relation;
            }

            @Override
            public void onClick(View view) {
                // relation contains the ID, use it to resolve which record to update in the DB
                Log.i("RELATIONS ON CLICK", relation.getId());
                mDatabase.child(String.valueOf(relation.getId())).child("id").removeValue();
                mDatabase.child(String.valueOf(relation.getId())).child("name").removeValue();
                mDatabase.child(String.valueOf(relation.getId())).child("phone").removeValue();
                mDatabase.child(String.valueOf(relation.getId())).child("address").removeValue();
                mDatabase.child(String.valueOf(relation.getId())).child("relationship").removeValue();
                onUpdateRelationButtonClick(context, relation);
            }
        }
    }

    // contains elements in each item in the listview
    public static class RelationViewHolder {
        private TextView relationName;
        private TextView relationPhone;
        private ImageButton deleteButton;
    }

    // when an activity spawned from an intent ends and returns a result, it calls this emthod
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            // check to see whetehr user pressed submit or hit back
            if(resultCode == Activity.RESULT_OK) {

                // get object from resultant intent
                /*String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                String address = data.getStringExtra("address");
                String relationship = data.getStringExtra("relationship");
*/
                Relation relation = data.getParcelableExtra("relation");
                mDatabase
                        .child(String.valueOf(relation.getId())).child("id").setValue(String.valueOf(relation.getId()));
                mDatabase.child(String.valueOf(relation.getId())).child("name").setValue(relation.getName());
                mDatabase.child(String.valueOf(relation.getId())).child("phone").setValue(relation.getPhone());
                mDatabase.child(String.valueOf(relation.getId())).child("address").setValue(relation.getAddress());
                mDatabase.child(String.valueOf(relation.getId())).child("relationship").setValue(relation.getRelationship());
                //listManager.addRelation(relation);
                //adapter.swapRelations(listManager.getList());
                Toast.makeText(this, "Added " + relation.getName() +
                    " to list of relations",
                    Toast.LENGTH_LONG).show();

                // refresh listview to display new object


            }
        } else if (requestCode == 2) {
            // update relation
            if(resultCode == Activity.RESULT_OK) {
                Relation relation = data.getParcelableExtra("relation");
                //listManager.updateRelation(relation);
                //adapter.swapRelations(listManager.getList());
                mDatabase
                        .child(String.valueOf(relation.getId())).child("id").setValue(String.valueOf(relation.getId()));
                mDatabase.child(String.valueOf(relation.getId())).child("name").setValue(relation.getName());
                mDatabase.child(String.valueOf(relation.getId())).child("phone").setValue(relation.getPhone());
                mDatabase.child(String.valueOf(relation.getId())).child("address").setValue(relation.getAddress());
                mDatabase.child(String.valueOf(relation.getId())).child("relationship").setValue(relation.getRelationship());
                Toast.makeText(this, "Updated " + relation.getName() +
                    " in list of relations",
                    Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("relation", "something went very wrong");
        }
    }

}

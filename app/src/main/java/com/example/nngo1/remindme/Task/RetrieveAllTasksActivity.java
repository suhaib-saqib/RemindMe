package com.example.nngo1.remindme.Task;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nngo1.remindme.Event.Event;
import com.example.nngo1.remindme.R;
import com.example.nngo1.remindme.Relation.RetrieveAllRelationsActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAllTasksActivity extends AppCompatActivity {
    private TaskManager listManager;
    private TaskAdapter adapter;
    private DatabaseReference mDatabase;
    private ArrayList<Task> tasks;

    private void onAddTaskButtonClick() {
        Intent taskIntent = new Intent(this, CreateTaskActivity.class);
        startActivityForResult(taskIntent, 1);
    }

    private void onUpdateTaskButtonClick(Context context, Task task) {
        Intent taskIntent = new Intent(this, UpdateTaskActivity.class);
        taskIntent.putExtra("task", task);
        startActivityForResult(taskIntent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_all_tasks);
        ListView taskList = (ListView) findViewById(R.id.task_list);
        listManager = new TaskManager(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("task");

        tasks = new ArrayList<>();

        adapter = new TaskAdapter(
                this,
                tasks
        );
        taskList.setAdapter(adapter);
        ImageButton addButton = (ImageButton) findViewById(R.id.add_task);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTaskButtonClick();
            }
        });
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task task = dataSnapshot.getValue(Task.class);
                adapter.add(task);
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

    private class TaskAdapter extends ArrayAdapter<Task> {

        private Context context;
        private List<Task> tasks;
        private LayoutInflater inflater;

        public TaskAdapter(
                Context context,
                List<Task> tasks
        ) {
            super(context, -1, tasks);
            this.context = context;
            this.tasks = tasks;
            this.inflater = LayoutInflater.from(context);
        }

        public void swapTasks(List<Task> tasks) {
            this.tasks = tasks;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ItemViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.task_layout, parent, false);
                Log.d("GETVIEW", "position: " + position);
                holder = new ItemViewHolder();
                holder.itemDescription = (TextView) convertView.findViewById(R.id.task);
                holder.itemState = (CheckBox) convertView.findViewById(R.id.is_complete);
                holder.itemDateAdded = (TextView) convertView.findViewById(R.id.date_added);
                holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_task);
                holder.editButton = (ImageButton) convertView.findViewById(R.id.edit_task);
                holder.deleteButton.setOnClickListener(new DeleteOnClickListener(getApplicationContext(), position));
                holder.editButton.setOnClickListener(new UpdateOnClickListener(getApplicationContext(), tasks.get(position)));
                convertView.setTag(holder);
            } else {
                holder = (ItemViewHolder) convertView.getTag();
            }

            holder.itemDescription.setText(tasks.get(position).getDescription());
            holder.itemState.setChecked(tasks.get(position).isComplete());
            holder.itemDateAdded.setText(tasks.get(position).getDateAdded());
            holder.itemState.setTag(tasks.get(position));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = (Task) holder.itemState.getTag();
                    task.toggleComplete();
                    listManager.updateTask(task);
                    notifyDataSetChanged();
                }
            });

            holder.itemState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task item = (Task) holder.itemState.getTag();
                    item.toggleComplete();
                    listManager.updateTask(item);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public class DeleteOnClickListener implements View.OnClickListener {

            private int index;
            private Context context;

            public DeleteOnClickListener(Context context, int index) {
                this.context = context;
                this.index = index;
            }

            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(RetrieveAllTasksActivity.this)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child(String.valueOf(tasks.get(index).getId())).child("id").removeValue();
                                mDatabase.child(String.valueOf(tasks.get(index).getId())).child("description").removeValue();
                                mDatabase.child(String.valueOf(tasks.get(index).getId())).child("date").removeValue();
                                mDatabase.child(String.valueOf(tasks.get(index).getId())).child("isCompleted").removeValue();
                           }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        }

        class UpdateOnClickListener implements View.OnClickListener {
            private Task task;
            private Context context;


            public UpdateOnClickListener(Context context, Task task) {
                this.context = context;
                this.task = task;
            }
            @Override
            public void onClick(View view) {
                mDatabase.child(String.valueOf(task.getId())).child("id").removeValue();
                mDatabase.child(String.valueOf(task.getId())).child("description").removeValue();
                mDatabase.child(String.valueOf(task.getId())).child("date").removeValue();
                mDatabase.child(String.valueOf(task.getId())).child("isCompleted").removeValue();
                onUpdateTaskButtonClick(context, task);
            }
        }
    }

    public static class ItemViewHolder {
        public TextView itemDescription;
        public CheckBox itemState;
        public TextView itemDateAdded;
        public ImageButton deleteButton;
        public ImageButton editButton;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Task task = data.getParcelableExtra("task");
               // listManager.addTask(task);
               // adapter.swapTasks(listManager.getList());
                mDatabase.child(task.getId()).child("id").setValue(task.getId());
                mDatabase.child(task.getId()).child("description").setValue(task.getDescription());
                mDatabase.child(task.getId()).child("date").setValue(task.getDateAdded());
                mDatabase.child(task.getId()).child("isCompleted").setValue(task.isComplete());

                Toast.makeText(this, "Added " + task.getDescription() +
                                " to list of tasks",
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                Task task = data.getParcelableExtra("task");
                //listManager.updateTask(task);
                //adapter.swapTasks(listManager.getList());

                mDatabase.child(task.getId()).child("id").setValue(task.getId());
                mDatabase.child(task.getId()).child("description").setValue(task.getDescription());
                mDatabase.child(task.getId()).child("date").setValue(task.getDateAdded());
                mDatabase.child(task.getId()).child("isCompleted").setValue(task.isComplete());

                Toast.makeText(this, "Updated " + task.getDescription() +
                                " in list of events",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("event", "error on activity result");
        }
    }

}

package edu.maine.allen3.christopher.androidtasklist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;
import java.util.Date;
import android.widget.TextView;
import android.widget.Button;

public class NewTaskActivity extends AppCompatActivity {
    EditText taskName;
    EditText taskDescription;
    DatePicker taskDate;
    Switch taskCompleted;
    TextView activityLabel;
    Button cancelButton;

    /**
     * Creates a blank view if the user is adding a new task,
     * fills in widgets when the user is editing a task.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_edit_toolbar);
        setSupportActionBar(toolbar);
        taskName = (EditText) findViewById(R.id.newtask_name);
        taskDescription = (EditText) findViewById(R.id.newtask_desc);
        taskDate = (DatePicker) findViewById(R.id.newtask_date);
        taskCompleted = (Switch) findViewById(R.id.newtask_completeSwitch);
        activityLabel = (TextView) findViewById(R.id.add_edit_label);
        cancelButton = (Button) findViewById(R.id.close_button);

        if( getIntent().hasCategory("editTask"))
        {
            Task editTask = (Task) getIntent().getSerializableExtra("newEditTask");
            taskName.setText(editTask.getName());
            taskDescription.setText(editTask.getDescription());
            taskDate.updateDate(editTask.getCmpDate().getYear(), editTask.getCmpDate().getMonth() - 1,
                    editTask.getCmpDate().getDay());
            taskCompleted.setChecked(editTask.isCompleted());
            activityLabel.setText("Edit Task");
        }

        //When cancel button is pressed, do the same as a back button press
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    /**
     * When the save button is pressed, this method handles what to do if
     * 1)The user has not filled in a name for the task
     * 2)The user is creating a new task
     * 3)The user is editing an existing task
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_save) {
            String taskString = taskName.getText().toString();
            if(taskString.equals("")) {
                Toast.makeText(this, "You need to add a name for the task before you can " +
                        "save it", Toast.LENGTH_SHORT ).show();
            }
            else if(getIntent().hasCategory("editTask")) {
                Intent addThisTask = new Intent( this, TaskListActivity.class);
                Task addTask = new Task(taskString, taskDescription.getText().toString(),
                        new Date(taskDate.getYear(), taskDate.getMonth() + 1, taskDate.getDayOfMonth()),
                        taskCompleted.isChecked());
                addThisTask.putExtra("newEditToList", addTask );
                addThisTask.addCategory("addNewEditText");
                android.util.Log.d("ResultIn", "TaskName: " + addTask.getName());
                android.util.Log.d("ResultIn", "TaskDate " + addTask.getDate());
                setResult(1, addThisTask);
                finish();
            }
            else {
                Intent addThisTask = new Intent( this, TaskListActivity.class);
                Task addTask = new Task(taskString, taskDescription.getText().toString(),
                        new Date(taskDate.getYear(), taskDate.getMonth() + 1, taskDate.getDayOfMonth()),
                        taskCompleted.isChecked());
                addThisTask.putExtra("newTaskToList", addTask );
                addThisTask.addCategory("addNewTask");
                setResult(0, addThisTask);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is needed for when the back button is pressed
     * because this activity was launched expecting a result.
     * This code returns an empty result when the button is pressed
     * so that the main activity knows this is done and it does not
     * need to do anything.
     */
    @Override
    public void onBackPressed() {
        Intent doNothing = new Intent(this, TaskListActivity.class);
        setResult(-1, doNothing );
        finish();
    }
}

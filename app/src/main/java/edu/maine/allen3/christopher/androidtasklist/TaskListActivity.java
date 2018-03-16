package edu.maine.allen3.christopher.androidtasklist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.Date;

import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.content.Context;

public class TaskListActivity extends AppCompatActivity {


    private ListView listItems;
    private ArrayList<Task> toDoItems;
    private TaskRowAdapter taskToList;
    private Context taskContext;
    private int currentRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listItems = (ListView) findViewById(R.id.listTasks);

        toDoItems = new ArrayList<Task>();
        addStartingTasks();

        taskToList = new TaskRowAdapter(toDoItems, getApplicationContext());

        listItems.setAdapter(taskToList);

        taskContext = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskItem();
            }
        });
        //When a row is pressed, editTaskItem is called and NewTaskActivity is launched
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                editTaskItem(position);
            }
        });
    }
    /**
     * Launches NewTaskActivity for a result
     */
    private void addTaskItem() {
        Intent addTask = new Intent( taskContext, NewTaskActivity.class);
        startActivityForResult( addTask, 0 );
    }
    /**
     * Launches the NewTaskActivity for a result, and passes in the current task for editing
     * @param position
     */
    private void editTaskItem( int position ) {
        currentRow = position;
        Task editTask = toDoItems.get(position);
        Intent newEditTask = new Intent(taskContext, NewTaskActivity.class);
        newEditTask.putExtra("newEditTask", editTask);
        newEditTask.addCategory("editTask");
        startActivityForResult(newEditTask, 1);
    }
    /**
     * When an add or edit activity is finished, the information is handled here
     * The add case simply adds the newly created task.
     * The edit case removes the old version of the task and then adds the new version
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode != -1) {
            Task addThisTask = (Task) data.getSerializableExtra("newTaskToList");
            addTask(addThisTask);
            taskToList.notifyDataSetChanged();
        }
        else if(requestCode == 1 && resultCode != -1) {
            toDoItems.remove(currentRow);
            Task addThisTask = (Task) data.getSerializableExtra("newEditToList");
            addTask(addThisTask);
            taskToList.notifyDataSetChanged();
        }
    }
    //Adds starting tasks
    private void addStartingTasks() {
        int day1 = 4;
        int day2 = 10;
        int day3 = 21;
        int month1 = 6;
        int month2 = 3;
        int month3 = 11;
        int year = 2018;
        Date firstDate = new Date(2018, month1, day1);
        Date secondDate = new Date(2018, month2, day2);
        Date thirdDate = new Date(2018, month3, day3);

        Task firstTask = new Task( "Get Grocieries", "Milk, Eggs, Snacks, Vegetables",
                firstDate, false);
        Task secondTask = new Task( "Walk Dog", "Bring Mia around neghborhood",
                secondDate, false);
        Task thirdTask = new Task( "Do Homework", "Micro: Page 36 #3,7,9,11",
                thirdDate, false);
        addTask(firstTask);
        addTask(secondTask);
        addTask(thirdTask);
    }
    /**
     * Uses the task compareTo method to add a new task in the
     * correct position.
     * @param addTask
     */
    private void addTask( Task addTask ) {
        if(toDoItems.size() < 1 )
            toDoItems.add(addTask);
        else {
            for (int i = 0; i < toDoItems.size(); i++) {
                if (addTask.compareTo(toDoItems.get(i)) < 0) {
                    toDoItems.add(i, addTask);
                    return;
                }
                else if (i == toDoItems.size() - 1) {
                    toDoItems.add(addTask);
                    return;
                }
            }
        }
    }
}

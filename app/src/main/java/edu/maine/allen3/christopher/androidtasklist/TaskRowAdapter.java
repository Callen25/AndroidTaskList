package edu.maine.allen3.christopher.androidtasklist;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
/**
 * Created by chrisallen on 3/10/18.
 */

public class TaskRowAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> taskList;
    Context context;
    private static LayoutInflater inflater = null;

    public TaskRowAdapter(ArrayList<Task> tasks, Context ncontext) {
       super(ncontext, R.layout.row, tasks);
       taskList = tasks;
       context = ncontext;
    }

    private static class ViewHolder {
        TextView taskName, taskDescription, taskDate;
        CheckBox taskDone;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return taskList.size();
    }

    @Override
    public Task getItem(int position) {
        // TODO Auto-generated method stub
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Task task = getItem(position);
        ViewHolder viewHolder;
        final View thisView;

        //If view is empty, populate it
        if( convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.taskName = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.taskDescription = (TextView) convertView.findViewById(R.id.task_description);
            viewHolder.taskDate = (TextView) convertView.findViewById(R.id.task_date);
            viewHolder.taskDone = (CheckBox) convertView.findViewById(R.id.doneCheck);
            android.util.Log.d("Cell", "Was called");

            thisView = convertView;

            convertView.setTag(viewHolder);

        }
        //If view is not empty, return it
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            thisView = convertView;
            /**
             * Listens for a click on the checkbox, if it is clicked,
             * it is marked completed/incomplete. It is removed from its current
             * position and added to where it should be
             */
            viewHolder.taskDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.util.Log.d("CheckBox", "Tick Registered");
                    Integer realPosition = (Integer) view.getTag();
                    task.switchComplete();
                    taskList.remove(task);
                    addTask(task);
                    notifyDataSetChanged();
                }
            });
        }

        viewHolder.taskName.setText(task.getName());
        viewHolder.taskDescription.setText(task.getDescription());
        viewHolder.taskDate.setText(task.getDate());
        viewHolder.taskDone.setChecked(task.isCompleted());

        return convertView;
    }
    //Adds task to proper location in arrayList/Adapter
    private void addTask( Task addTask ) {
        if(taskList.size() < 1 )
            taskList.add(addTask);
        else {
            for (int i = 0; i < taskList.size(); i++) {
                if (addTask.compareTo(taskList.get(i)) < 0) {
                    taskList.add(i, addTask);
                    return;
                }
                else if (i == taskList.size() - 1) {
                    taskList.add(addTask);
                    return;
                }
            }
        }
    }

}

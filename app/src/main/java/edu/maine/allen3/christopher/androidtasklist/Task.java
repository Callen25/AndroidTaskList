package edu.maine.allen3.christopher.androidtasklist;

/**
 * Created by chrisallen on 3/10/18.
 */
import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable, Comparable<Task> {
    private String taskName, taskDescription;
    private Date taskDate;
    private Boolean taskIsCompleted;

    //Instance methodd
    public Task( String name, String description, Date date, Boolean isDone )
    {
        taskName = name;
        taskDescription = description;
        taskDate = date;
        taskIsCompleted = isDone;
    }

    public String getName() {
        return taskName;
    }

    public String getDescription() {
        return taskDescription;
    }

    //Returns a string of the date, used in the listView
    public String getDate() {
        return taskDate.getMonth() + "/" + taskDate.getDate() + "/" + taskDate.getYear();
    }

    //Accessor for taskDate
    public Date getCmpDate() { return taskDate; }

    //Accessor for taskIsCompleted
    public Boolean isCompleted() { return taskIsCompleted; }

    //Allows for completed state to be easily changed from its previous state
    public void switchComplete() { taskIsCompleted = !taskIsCompleted; }

    /**
     *
     * @param cmpTask
     * @return
     * This method compares task so that they are in ascending
     * order by date and also completedness. Completed items
     * are also in ascending order.
     */
    public int compareTo(Task cmpTask) {
        if( this.isCompleted() )
        {
            if( cmpTask.isCompleted() )
            {
                if( this.getCmpDate().before(cmpTask.getCmpDate()))
                    return -1;
                else
                    return 1;
            }
            else
                return 1;
        }
        else {
            if( cmpTask.isCompleted() ) {
                return -1;
            }
            else {
                if( this.getCmpDate().before(cmpTask.getCmpDate()))
                    return -1;
                else
                    return 1;
            }
        }
    }
}

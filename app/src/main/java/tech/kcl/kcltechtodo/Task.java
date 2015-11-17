package tech.kcl.kcltechtodo;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.Telephony;

import org.joda.time.DateTime;

/**
 * Created by igaln on 10/11/2015.
 */
public class Task {

    private int id = -1;
    private String title;
    private String notes;
    private DateTime dueDate;
    private boolean isComplete;

    public Task(String title, String notes, DateTime dueDate, boolean complete) {
        this.title = title;
        this.notes = notes;
        this.dueDate = dueDate;
        this.isComplete = complete;
    }
    public Task(Cursor input) {
        id = input.getInt(input.getColumnIndex("id"));
        title = input.getString(input.getColumnIndex("title"));
        notes = input.getString(input.getColumnIndex("notes"));
        dueDate = new DateTime(input.getInt(input.getColumnIndex("due_date")));
        isComplete = input.getInt(input.getColumnIndex("is_complete")) == 1;
    }

    public ContentValues getContentValuese() {
        ContentValues output = new ContentValues();

        if (id > 0)
            output.put("id", id);

        output.put("title", title);
        output.put("notes", notes);
        output.put("due_date", dueDate.getMillis());
        output.put("is_complete", isComplete ? 1 : 0);

        return output;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}

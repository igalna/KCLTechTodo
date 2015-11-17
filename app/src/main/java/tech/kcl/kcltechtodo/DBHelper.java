package tech.kcl.kcltechtodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by igaln on 10/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "KclTechTodo";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 0:
                // create initial DB
                db.execSQL("CREATE TABLE Tasks (" +
                        "id INTEGET PRIMARY KEY," +
                        "title TEXT," +
                        "notes TEXT," +
                        "due_date INTEGER," +
                        "is_complete INTEGER );");
            case 1:
                // upgrade from 1 to two
            case 2:
                // upgrade from 2
            case 3:
                // upgrade from 4

                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void saveTask(Task t) {
        SQLiteDatabase db = getWritableDatabase();
        if (db == null)
            return;

        db.insertWithOnConflict(
                "Tasks",
                null,
                t.getContentValues(),
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }


    public ArrayList<Task> getIncompleteTasks() {
        // create an empty ArrayList to build our output
        ArrayList<Task> output = new ArrayList<>();

        // get a readable instance of the database
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) return output;

        // run the query to get all incomplete tasks
        Cursor rawTasks = db.rawQuery("SELECT * FROM Tasks WHERE is_complete = 0 ORDER BY due_date ASC;", null);

        // move the cursor to the first result (or skip this section if there are no results)
        if (rawTasks.moveToFirst()) {
            // iterate through results
            do {
                // convert the cursor version of the task to a real Task object and add it to output
                output.add(new Task(rawTasks));
            } while (rawTasks.moveToNext());
        }

        // we're done with the cursor and database, so we can close them here
        rawTasks.close();
        db.close();

        // return the (possibly empty) list
        return output;

    }

    @Nullable
    public Task getTask(int id) {
        // "holder" for our output, which starts as null
        Task output = null;

        // get a readable instance of the database
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) return null;

        // run the query to get the matching task
        Cursor rawTask = db.rawQuery("SELECT * FROM Tasks WHERE id = ? ORDER BY due_date ASC;", new String[]{String.valueOf(id)});

        // move the cursor to the first result, if one exists
        if (rawTask.moveToFirst()) {
            // convert the cursor to a task and assign it to our output
            output = new Task(rawTask);
        }

        // we're done with the cursor and database, so we can close them here
        rawTask.close();
        db.close();

        // return the (possibly null) output
        return output;
    }


    /*Cursor task22 = db.rawQuery("SELECT * " +
            "FROM Tasks" +
            "WHERE id = ?;", new String[] { "22"});
            */
}
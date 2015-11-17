package tech.kcl.kcltechtodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                t.getContentValuese(),
                SQLiteDatabase.CONFLICT_REPLACE
        );
    }


    public ArrayList<Task> getUncompleteTasks () {
        ArrayList<Task> output = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        if (db == null)
            return output;

        Cursor allTasks = db.rawQuery("SELECT * " +
                "FROM Tasks " +
                "WHERE is_complete = 0 " +
                "ORDER BY due_date ASC;", null);

        if(allTasks.moveToFirst()) {
            do {
                // somethinge with cursor
                output.add(new Task(allTasks));
            } while (allTasks.moveToNext());
        }

        return output;

    }


    /*Cursor task22 = db.rawQuery("SELECT * " +
            "FROM Tasks" +
            "WHERE id = ?;", new String[] { "22"});
            */
}
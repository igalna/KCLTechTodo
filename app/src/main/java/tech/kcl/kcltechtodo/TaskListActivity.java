package tech.kcl.kcltechtodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    // view components
    private ProgressBar loadingIcon;
    private ListView listView;
    private TextView noTasksMessage;

	/*================*
     * Activity setup *
	 *================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the layout of this activity
        setContentView(R.layout.activity_task_list);

        // set the string for the title bar
        setTitle(getString(R.string.task_list_activity_title));

        // find UI components
        loadingIcon = (ProgressBar) findViewById(R.id.loading_icon);
        listView = (ListView) findViewById(R.id.list_view);
        noTasksMessage = (TextView) findViewById(R.id.no_tasks_message);



        String[] data = {"Anna", "Victor", "Jordan",
                         "Steve", "Mark", "Carmichael",
                         "Richard", "Sarah",
                         "Casey", "Willey",
                         "Anna", "Victor", "Jordan",
                         "Steve", "Mark", "Carmichael",
                         "Richard", "Sarah",
                         "Casey", "Willey",
                         "Anna", "Victor", "Jordan",
                         "Steve", "Mark", "Carmichael",
                         "Richard", "Sarah",
                         "Casey", "Willey",
                         "Anna", "Victor", "Jordan",
                         "Steve", "Mark", "Carmichael",
                         "Richard", "Sarah",
                         "Casey", "Willey"};

        BuildXNameAdapter myAdapter = new BuildXNameAdapter(this, data);
        listView.setAdapter(myAdapter);

        loadingIcon.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);


        // PREFERENCES //

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefEditor = prefs.edit();

        prefEditor.putInt("user_highscore", 9001);
        prefEditor.putBoolean("welcome_tour_finished", true);
        prefEditor.putString("notification_alert_sound", "bells");

        prefEditor.apply();

        int highScore = prefs.getInt("user_highscore", 0);
        boolean tourFinished = prefs.getBoolean("welcome_tour_finished", false);
        String alertSound = prefs.getString("notification_alert_sound", null);

        String somethingElse = prefs.getString("unknown_key", "default_value");


        Toast.makeText(this, "alert : " + alertSound + "\nsomething else : " + somethingElse + "\nhigh score : " + highScore, Toast.LENGTH_LONG).show();


        // DB INSERT //

        Task task1 = new Task("Buy Milk", "The Green Stuff", DateTime.now(), false);
        Task task2 = new Task("Buy Bread", "Wholemeal Bread", DateTime.now(), false);
        Task task3 = new Task("Buy Tickets", "", DateTime.now(), false);

        DBHelper dbHelper = new DBHelper(this);

        dbHelper.saveTask(task1);
        dbHelper.saveTask(task2);
        dbHelper.saveTask(task3);


        // DB READ //

        ArrayList<Task> tasks = dbHelper.getUncompleteTasks();
        for (Task t : tasks) {
            // Do something here
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_list_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_task:
                startActivity(new Intent(TaskListActivity.this, EditTaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
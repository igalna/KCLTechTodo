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

    private ArrayList<Task> taskList = new ArrayList<>();
    private DBHelper dbHelper = null;
    private TaskListAdapter taskListAdapter;


    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
        dbHelper = null;
    }

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

        taskListAdapter = new TaskListAdapter(this);
        listView.setAdapter(taskListAdapter);

        Task task1 = new Task("Buy Milk", "The Green Stuff", DateTime.now(), false);
        Task task2 = new Task("Buy Bread", "Wholemeal Bread", DateTime.now(), false);
        Task task3 = new Task("Buy Tickets", "", DateTime.now(), false);

        DBHelper dbHelper = new DBHelper(this);

        dbHelper.saveTask(task1);
        dbHelper.saveTask(task2);
        dbHelper.saveTask(task3);

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

    @Override
    protected void onResume() {
        super.onResume();
        refreshTasks();
    }

    private void refreshTasks() {
        loadingIcon.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        noTasksMessage.setVisibility(View.GONE);

        taskList = dbHelper.getUncompleteTasks();
        taskListAdapter.setTasks(taskList);
        taskListAdapter.notifyDataSetChanged();

        loadingIcon.setVisibility(View.GONE);

        if (taskList.isEmpty())
            noTasksMessage.setVisibility(View.VISIBLE);
        else
            listView.setVisibility(View.VISIBLE);
    }
}
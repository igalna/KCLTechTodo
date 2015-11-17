package tech.kcl.kcltechtodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTime;

/*======================================================================

** Activities **

This is an activity, one of the basic building blocks of an Android app.
An activity can be thought of as a distinct "page" in your app. They
have layouts made up of many Views (taken from an XML resource), they
can handle user interactions, and your user can navigate between them.

Activities have a fairly complex lifecycle that they travel through as
they are created and destroyed. We will cover the four main events that
an Activity can go through in the lecture, but here's a summary:

onCreate: the activity has been created for the first time, but may not
          be visible to the user yet

onResume: the activity is now "on top" and can be seen by the user

onPause:  the activity has been obscured by something (another app
          opening, the user pressing "Home", a phone call, etc.)

onStop:   the activity has been stopped and will terminate completely

The most important thing to know about the lifecycle is that an activity
can exist in the background without being fully closed. For example, a
user is using your app and then receives a phone call - the call covers
your activity, but it isn't fully closed; it's "paused". It will be
"resumed" when their call ends.

======================================================================*/

public class EditTaskActivity extends AppCompatActivity {

    // view components
    private ProgressBar loadingIcon;
    private ViewGroup mainContent;
    private EditText titleInput;
    private DatePicker dueDateInput;
    private EditText notesInput;

    // activity state
    private boolean createNew = true;
    private int editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("task_id")) {
            createNew = false;
            editId = extras.getInt("task_id");
        }

        // set the layout of this activity
        setContentView(R.layout.activity_edit_task);

        // TODO: set the string for the title bar

        // find UI components
        loadingIcon = (ProgressBar) findViewById(R.id.loading_icon);
        mainContent = (ViewGroup) findViewById(R.id.main_content);
        titleInput = (EditText) findViewById(R.id.title_input);
        dueDateInput = (DatePicker) findViewById(R.id.due_date_input);
        notesInput = (EditText) findViewById(R.id.notes_input);
        Button saveButton = (Button) findViewById(R.id.save_button);

        // set up the date picker
        dueDateInput.setCalendarViewShown(false);

        // switch to the form view from the loading view
        loadingIcon.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });



        if (!createNew) {
            Task editingTask = new DBHelper(this).getTask(editId);
            if (editingTask != null) {

            }
        }
    }


    private void saveTask() {
        String title = titleInput.getText().toString();
        String notes = notesInput.getText().toString();
        DateTime dateTime = new DateTime(
                dueDateInput.getYear(),
                dueDateInput.getMonth() + 1,
                dueDateInput.getDayOfMonth(),
                0, 0 , 0
        );

        // make a new task
        Task task = new Task(title, notes, dateTime, false);
        if (editId  > 0) {
            task.setId(editId);
        }

        // save task in the database

        new DBHelper(this).saveTask(task);

        // send the user back
        finish();
    }
}

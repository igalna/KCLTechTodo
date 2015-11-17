package tech.kcl.kcltechtodo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by igaln on 17/11/2015.
 */
public class TaskListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Task> tasks = null;

    public TaskListAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        if (tasks == null) return 0;
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        if (tasks == null) return null;
        return tasks.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View recycledView = convertView;

        if (recycledView == null) {
            LayoutInflater li = activity.getLayoutInflater();
            recycledView = li.inflate(R.layout.list_view_row, parent, false);
        }

        TextView titleView = (TextView) recycledView.findViewById(R.id.title);
        Task task = (Task) getItem(position);
        titleView.setText(task.getTitle());

        return recycledView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

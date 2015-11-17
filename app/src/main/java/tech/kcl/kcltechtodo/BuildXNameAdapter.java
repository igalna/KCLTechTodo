package tech.kcl.kcltechtodo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by igaln on 03/11/2015.
 */
public class BuildXNameAdapter extends BaseAdapter {

    private String[] data;
    private Activity activity;

    public BuildXNameAdapter(Activity activity, String[] data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (data == null) return 0;
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        if (data == null) return null;
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View recycledView, ViewGroup parent) {
        View output = recycledView;
        if (output == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            output = inflater.inflate(R.layout.list_view_row, parent, false);
        }

        TextView textView = (TextView) output.findViewById(R.id.name);
        textView.setText(getItem(position).toString());


        return output;
    }
}

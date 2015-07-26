package nl.vandoren.app.uraandroid.Fragment.WeekPlanning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 25-6-2015.
 */
public class WeekPlanning_adapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

    public ArrayList<Triple> myData = new ArrayList<>();

    private LayoutInflater mInflater;

    private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();


    public WeekPlanning_adapter(Context c) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String day, final String project, final String taskName,
                         final String hours) {
        myData.add(new Triple(day, project, taskName, hours));

    }

    public void addSeparatorItem(final String day) {
        //I add 1, because will be added additionally one row extra, day name
        myData.add(new Triple(day));

        //separation position
        mSeparatorsSet.add(myData.size() - 1);
    }

    public void clearItems() {
        mSeparatorsSet.clear();
        myData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    public int getCount() {
        return myData.size();
    }

    public Triple getItem(int position) {
        return myData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderSeparator = null;
        ViewHolder.DataViewHolder holderData = null;
        int type = getItemViewType(position);
        Triple temptriple = getItem(position);

        //fill in fields
        if (type == TYPE_ITEM) //Task row (ex: a1221MaUa Storing 8.0)
        {
            if (convertView == null) {
                holderData = new ViewHolder().new DataViewHolder();

                convertView = mInflater.inflate(R.layout.fragment_workedhours_list_item, null);
                holderData.textProject = (TextView) convertView.findViewById(R.id.id_wokedh_item_project);
                holderData.textTask = (TextView) convertView.findViewById(R.id.id_wokedh_item_task);
                holderData.textHours = (TextView) convertView.findViewById(R.id.id_wokedh_item_hours);
                convertView.setTag(holderData);

            } else {
                holderData = (ViewHolder.DataViewHolder) convertView.getTag();
            }
            holderData.textProject.setText(temptriple.project);
            holderData.textTask.setText(temptriple.taskName);
            holderData.textHours.setText(String.format("%.2f", Float.parseFloat(temptriple.hours)));
        } else  //Day row (ex: maandag, dinsdag, etc . . .)
        {
            if (convertView == null) {
                holderSeparator = new ViewHolder();
                convertView = mInflater.inflate(R.layout.fragment_workedhours_list_item_header, null);
                holderSeparator.dayNameText = (TextView) convertView.findViewById(R.id.id_wokedh_item_header);
                convertView.setTag(holderSeparator);
            } else {
                holderSeparator = (ViewHolder) convertView.getTag();
            }
            convertView.setOnClickListener(null); // day row is not clickable
            holderSeparator.dayNameText.setText(temptriple.day);
        }
        return convertView;
    }


    /**
     * Holds list rows in memory for better performance while scrolling
     */
    public class ViewHolder {

        public TextView dayNameText; //dayName

        public class DataViewHolder extends ViewHolder {
            public TextView textProject;
            public TextView textTask;
            public TextView textHours;
        }
    }

    public class Triple {
        String day;
        String project;
        String taskName;
        String hours;

        Triple(String day) {
            this.day = day;
        }

        Triple(String day, String project, String taskName, String hours) {
            this.day = day;
            this.project = project;
            this.taskName = taskName;
            this.hours = hours;
        }
    }
}
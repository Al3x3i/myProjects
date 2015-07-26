package nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 6/2/2015.
 */
public class Calendar_adapter extends BaseAdapter {
    private Context mContext;

    private java.util.Calendar date;
    private java.util.Calendar calendar;

    String itemvalue, curentDateString, selectedDate;
    DateFormat df;
    public List<String> dayString;

    /**
     * first color for selected date and second for unselected date
     */
    int [] color = new int[]{Color.parseColor("#D9E021"),Color.parseColor("#ffffff")};
    String[] days = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT","SUN"};

    public Calendar_adapter()
    {
        dayString = new ArrayList<String>();
        calendar = (GregorianCalendar)Calendar.getInstance().clone();

    }

    public void setNewProperties (Context c, GregorianCalendar monthCalendar, DateFormat format, String currentDate) {
        date = monthCalendar;
        mContext = c;
        df = format;
        curentDateString = currentDate;
        refreshDays(monthCalendar);
    }


    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        TextView dayNameView;
        LinearLayout li;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.adapter_calendar_item, null);
        }
        li = (LinearLayout)v.findViewById(R.id.calendat_item);
        dayView = (TextView) v.findViewById(R.id.date);
        dayNameView = (TextView) v.findViewById(R.id.week);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2];
        // checking whether the day is in current month or not.

        if (dayString.get(position).equals(curentDateString)) {
            dayView.setTextColor(Color.RED);
        } else {
            dayView.setTextColor(Color.BLACK);
        }


        // if day is selected then highlight it
        if(dayString.get(position).equals(selectedDate)){
            li = (LinearLayout)v.findViewById(R.id.calendat_item);
            li.setBackgroundColor(color[0]);
        }else{
            li.setBackgroundColor(color[1]);
        }

        dayView.setText(gridvalue);
        String day = days[position];
        dayNameView.setText(day);

        return v;
    }


    /**
     * Refresh week day numbers.
     * @param newDate new week date
     */
    public void refreshDays(GregorianCalendar newDate) {
        // clear items
        dayString.clear();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, newDate.get(GregorianCalendar.WEEK_OF_YEAR));
        calendar.set(Calendar.YEAR, newDate.get(GregorianCalendar.YEAR));

        //Takes first day of week and add 7 days to get right numbers of week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        for (int n = 0; n < 7; n++) {
            itemvalue = df.format(calendar.getTime());
            dayString.add(itemvalue);
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    /**
     * Method sets selected days, then calendar day will be highlighted .
     * To remove value need to pass value below zero
     * @param date day value, Starts from 0, (ex 0 = monday)
     */
    public void highlightSelectedDay(int date){
        if(date<0) {
            this.selectedDate = null;
        }else{
            this.selectedDate = dayString.get(date);
        }
    }
}

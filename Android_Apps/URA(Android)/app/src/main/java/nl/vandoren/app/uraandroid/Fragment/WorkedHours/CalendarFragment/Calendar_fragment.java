package nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 6/2/2015.
 */
public class Calendar_fragment extends Fragment {
    DateFormat df;
    private GridView gridview;
    public GregorianCalendar calendar;

    public Calendar_adapter adapter;// adapter instance

    MyOnClickListener_gridViewItemSelect gridViewItemClick;

    TextView title;
    LinearLayout rLayout;

    private onSomeEventListener someEventListener;
    private String curentDateString;
    public String selectedYear;
    public String selectedMonthNumeric;
    public String selectedMonthText;

    public Calendar_fragment() {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance().clone();
        adapter = new Calendar_adapter();
        gridViewItemClick =new MyOnClickListener_gridViewItemSelect();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

        //first I set current day (text becomes red)
        curentDateString = df.format(calendar.getTime());

        //to avoid new year error I set each week to first day
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale.setDefault(Locale.UK);
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_calendar, container, false);

        rLayout = (LinearLayout) rootView.findViewById(R.id.text);


        adapter.setNewProperties(getActivity(), calendar,df,curentDateString);


        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(gridViewItemClick);


        title = (TextView) rootView.findViewById(R.id.title);

        refreshCalendar();

        return rootView;
    }

    /**
     * Set next week in calendar
     */
    public void setNextWeek() {
        if (calendar.get(GregorianCalendar.WEEK_OF_YEAR) == calendar.getActualMaximum(GregorianCalendar.WEEK_OF_YEAR)+1) {
            //calendar.set(GregorianCalendar.WEEK_OF_YEAR, calendar.getActualMaximum(GregorianCalendar.WEEK_OF_YEAR)+1);
            int  i = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            if(i<4 && i <=7){ //According ISO standard January begins from >=Thursday
                calendar.set(GregorianCalendar.DAY_OF_MONTH,31);
            }else{
                calendar.set(GregorianCalendar.WEEK_OF_YEAR, calendar.get(GregorianCalendar.WEEK_OF_YEAR) + 1);
            }

        } else {
            calendar.set(GregorianCalendar.WEEK_OF_YEAR, calendar.get(GregorianCalendar.WEEK_OF_YEAR) + 1);
        }
    }

    /**
     * Set previous week in calendar
     */
    public void setPreviousWeek() {
        if (calendar.get(GregorianCalendar.WEEK_OF_YEAR)-1== calendar.getActualMinimum(GregorianCalendar.WEEK_OF_YEAR)) {
            int  i = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            if(i >=4 && i <=7){ //According ISO standard January begins from >=Thursday
                calendar.set(GregorianCalendar.DAY_OF_MONTH,1);
            }else{
                calendar.set(GregorianCalendar.WEEK_OF_YEAR, calendar.get(GregorianCalendar.WEEK_OF_YEAR) - 1);
            }

        } else {
            calendar.set(GregorianCalendar.WEEK_OF_YEAR, calendar.get(GregorianCalendar.WEEK_OF_YEAR) - 1);
        }

    }

    /**
     * refresh calendar day numbers
     */
    public void refreshCalendar() {
        adapter.refreshDays(calendar);
        adapter.notifyDataSetChanged();
        selectedYear = android.text.format.DateFormat.format("yyyy", calendar).toString();
        selectedMonthNumeric = Integer.toString(calendar.get(GregorianCalendar.WEEK_OF_YEAR));
        selectedMonthText = android.text.format.DateFormat.format("MMMM", calendar).toString();

        title.setText(selectedMonthText + " " + selectedYear + " (W" + calendar.get(GregorianCalendar.WEEK_OF_YEAR) + ")");
    }

    public void subscribeForCallBackEvent(onSomeEventListener listener){
        this.someEventListener = listener;
    }

    public interface onSomeEventListener{
        void onCalendarClickListener(int number, String selectedDate);
    }

    /**
     * Calendar gridView onItemClickListener. As well selected date will bi highlighted.
     */
    class MyOnClickListener_gridViewItemSelect implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(someEventListener !=null) {
                String selectedDate = adapter.dayString.get(i); //is used to send the service
                adapter.highlightSelectedDay(i);
                adapter.notifyDataSetChanged();
                someEventListener.onCalendarClickListener(i, selectedDate);
            }
        }
    }

}
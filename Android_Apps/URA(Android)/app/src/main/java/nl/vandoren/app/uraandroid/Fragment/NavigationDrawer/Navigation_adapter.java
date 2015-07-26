package nl.vandoren.app.uraandroid.Fragment.NavigationDrawer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 5/18/2015.
 */
public class Navigation_adapter extends BaseAdapter {

    List<Navigation_adapter_row> myRowItems;
    Activity myContext;

    public Navigation_adapter(Activity context, List<Navigation_adapter_row> rowItems){
        this.myContext = context;
        this.myRowItems = rowItems;
    }

    @Override
    public int getCount() {
        return myRowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return myRowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return myRowItems.indexOf(getItem(i));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        MyListViewHolder myViewHolder = null;

        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            view =  mInflater.inflate(R.layout.adapter_navigation_item, null);
            myViewHolder = new MyListViewHolder();
            myViewHolder.myTextView = (TextView) view.findViewById(R.id.title);
            myViewHolder.myImage = (ImageView) view.findViewById(R.id.icon);
            view.setTag(myViewHolder);
        }
        else{
            myViewHolder = (MyListViewHolder)view.getTag();
        }
        Navigation_adapter_row myRowItem = (Navigation_adapter_row)getItem(position);

        myViewHolder.myTextView.setText(myRowItem.myTitle);
        int i = myRowItem.myImageID;
        myViewHolder.myImage.setImageResource(myRowItem.myImageID);

        return view;
    }
    class MyListViewHolder{
        public TextView myTextView;
        public ImageView myImage;

    }
}

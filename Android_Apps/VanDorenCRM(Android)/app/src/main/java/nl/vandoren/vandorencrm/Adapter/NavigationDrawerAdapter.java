package nl.vandoren.vandorencrm.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.vandoren.vandorencrm.R;


/**
 * Created by Alex on 3/30/2015.
 */
public class NavigationDrawerAdapter extends ArrayAdapter
    {
        public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
        public final ArrayAdapter<String> headers;
        public final static int TYPE_SECTION_HEADER = 0;



        public NavigationDrawerAdapter(Context context, int resource) {
        super(context, resource);
        headers = new ArrayAdapter<String>(context, R.layout.fragment_navigation_drawer_header);
    }

    public void addSection(String section, Adapter adapter)
    {
        this.headers.add(section);
        this.sections.put(section, adapter);
    }



    public int getCount()
    {
        // total together all sections, plus one for each section header
        int total = 0;
        for (Adapter adapter : this.sections.values())
            total += adapter.getCount() + 1;
        return total;
    }



    @Override
    public int getItemViewType(int position)
    {
        int type = 1;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return TYPE_SECTION_HEADER;
            if (position < size) return type + adapter.getItemViewType(position - 1);

            // otherwise jump into next section
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int sectionnum = 0;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return headers.getView(sectionnum, convertView, parent);
            if (position < size) return adapter.getView(position - 1, convertView, parent);

            // otherwise jump into next section
            position -= size;
            sectionnum++;
        }
        return null;
    }
}
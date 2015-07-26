package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 6/15/2015.
 */
public class Dialog_projectList extends DialogFragment {

    ListView myLv;
    EditText myCompanyFilter;
    private CallBack callback;

    public final ArrayList<String> projectNames;
    ArrayAdapter<String> myAdapter;

    public Dialog_projectList() {
        projectNames = new ArrayList<>();
    }
    public void addProjectName(String names){
        this.projectNames.add(names);
    }


    public void setCallBack(CallBack callBack){
        try{
            callback = (CallBack)callBack;
        }
        catch (Exception ex){
            throw new ClassCastException("Fragment should implement CompanyList's Callback.");

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Projects");
        View v = inflater.inflate(R.layout.dialog_workedhours_projects, null);
        myLv = (ListView)v.findViewById(R.id.lv_dialog_projects);
        myCompanyFilter = (EditText)v.findViewById(R.id.id_edit_projectfilter);

        myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, (ArrayList<String>)projectNames.clone());
        myLv.setAdapter(myAdapter);

        setProjectButtonFilter();
        setOnListItemSelectCallBack();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //after showing soft keyboard title and list are resized.
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setProjectButtonFilter() {
        myCompanyFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myAdapter.clear();

                for(int counter = 0;counter<projectNames.size();counter++){
                    String g = charSequence.toString().toUpperCase();
                    if(projectNames.get(counter).toUpperCase().contains(charSequence.toString().toUpperCase())){
                        myAdapter.add(projectNames.get(counter));
                    }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setOnListItemSelectCallBack(){
        myLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedProject = myAdapter.getItem(i);
                int projectNrInList =-1; //project id number in list

                for(int x =0;x<projectNames.size();x++){
                    if(projectNames.get(x).equals(selectedProject)) {
                        projectNrInList = x;
                        break;
                    }
                }
                if(projectNrInList >=0) {
                    callback.onListViewItemSelected(projectNrInList);
                    dismiss();
                }
            }
        });
    }

    public interface CallBack{
        void onListViewItemSelected(int i);
    }
}

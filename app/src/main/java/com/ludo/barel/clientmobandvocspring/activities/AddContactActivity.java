package com.ludo.barel.clientmobandvocspring.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.controls.CtrParams;
import com.ludo.barel.clientmobandvocspring.models.Parametre;
import com.ludo.barel.clientmobandvocspring.utils.ButtonListener;
import com.ludo.barel.clientmobandvocspring.utils.Constants;
import com.ludo.barel.clientmobandvocspring.utils.ContactListAdapter;
import com.ludo.barel.clientmobandvocspring.utils.JsonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddContactActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> contactsToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        list = findViewById(R.id.subscribeContact);
        contactsToAdd = new ArrayList<>();
        new GetSubscribed().execute(list);
    }

    public void checkBoxhandler(View v){

        CheckBox cbox = (CheckBox) v;
        int position = Integer.parseInt(cbox.getTag().toString());
        View o = list.getChildAt(position).findViewById( R.id.rowBox);
        TextView name = o.findViewById(R.id.addContactName);
        if (cbox.isChecked()) {

            if(!name.getText().toString().isEmpty()){

                contactsToAdd.add(name.getText().toString());
            }
        }else if(contactsToAdd != null && !cbox.isChecked()){

          if(contactsToAdd.contains(name.getText().toString())){

                contactsToAdd.remove(name.getText().toString());
          }
        }
    }

    public void launchAdd(View v){

        //force refresh
        new addContacts().execute(contactsToAdd);
        Intent intent2 = new Intent(v.getContext(), HomeActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.addCategory(Intent.CATEGORY_HOME);
        v.getContext().startActivity(intent2);
    }

    @Override
    public void onBackPressed() {

        //force refresh
        Intent intent2 = new Intent(this, HomeActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent2);
    }

    private class addContacts extends AsyncTask<ArrayList<String>,Void,Void> {

        @Override
        protected Void doInBackground(ArrayList<String>... contacts) {

            try {

                ArrayList<String> contactMails =  contacts[0];
                String ipServeur = "";
                Parametre p = CtrParams.getInstance().getParametre();
                if(p != null && !p.getIpAddress().isEmpty()){

                    ipServeur = p.getIpAddress();
                }
                for(int i = 0; i < contactMails.size();i++){

                    String request ="http://"+ipServeur
                            +"/addContact?mailUser="+p.getPseudo()
                            +"&mailContact="+contactMails.get(i);

                    JsonManager.readJsonFromUrl(request);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetSubscribed extends AsyncTask<ListView,Void,Void> {

        @Override
        protected Void doInBackground(ListView... listViews) {

            try {

                String ipServeur = "";
                Parametre p = CtrParams.getInstance().getParametre();
                if(p != null && !p.getIpAddress().isEmpty()){

                    ipServeur = p.getIpAddress();
                }
                String request ="http://"+ipServeur+"/contact?mail=*";//char '*' allow to get all users subscribed just email are get no alias

                String res = JsonManager.readJsonFromUrl(request);
                JSONObject jsonObject  = new JSONObject(res);
                if(jsonObject != null){

                    JSONArray jarray = jsonObject.getJSONArray("_contacts");
                    ArrayList<String> names = new ArrayList<>();
                    if(jarray != null){

                        for (int i =0; i < jarray.length();i++){

                            JSONObject objC = new JSONObject(jarray.get(i).toString());
                            if(objC != null){

                                if (!objC.getString("mail").equals(p.getPseudo())) {

                                    names.add(objC.getString("mail"));
                                }
                            }
                        }
                        String[] finalNames = new String[names.size()];
                        finalNames = names.toArray(finalNames);
                        ListView l = listViews[0];
                        if(l != null){

                            AddContactListAdapter cla = new AddContactListAdapter(AddContactActivity.this, finalNames);
                            l.setAdapter(cla);
                        }
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    }

    private class AddContactListAdapter extends ArrayAdapter {

        private final Activity context;
        private final String[] names;

        public AddContactListAdapter(Activity context, String[] nameArrayParam) {

            super(context, R.layout.list_row , nameArrayParam);
            this.context = context;
            this.names = nameArrayParam;
        }

        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_detail, null,true);

            //gets references to objects in the list_row.xml file
            TextView nameTextField = (TextView) rowView.findViewById(R.id.addContactName);
            CheckBox cb = rowView.findViewById(R.id.check);
            cb.setTag(position);//set position of the checkbox
            //sets the values of the objects to values from the arrays
            nameTextField.setText(names[position]);
            return rowView;
        }
    }
}

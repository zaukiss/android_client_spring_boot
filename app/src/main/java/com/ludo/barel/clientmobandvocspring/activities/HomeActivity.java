package com.ludo.barel.clientmobandvocspring.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.controls.CtrParams;
import com.ludo.barel.clientmobandvocspring.models.Parametre;
import com.ludo.barel.clientmobandvocspring.utils.ButtonListener;
import com.ludo.barel.clientmobandvocspring.utils.Constants;
import com.ludo.barel.clientmobandvocspring.utils.ContactListAdapter;
import com.ludo.barel.clientmobandvocspring.utils.JsonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ImageButton contactBut =  findViewById(R.id.contact);
        ImageButton msgBut = findViewById(R.id.msg);
        ListView list =  findViewById(R.id.contactList);

        contactBut.setOnClickListener(new ButtonListener());
        msgBut.setOnClickListener(new ButtonListener());
        new GetContacts().execute(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this,SettingsActivity.class);
            try {//file exist

                Parametre p = CtrParams.getInstance().getParametre();
                intent.putExtra("ipAddress",p.getIpAddress());
                intent.putExtra("pseudo",p.getPseudo());
                intent.putExtra("notification",p.getNotificationPath());
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(intent);
            } catch (Exception e) {//file not exist no need to set text field

                startActivity(intent);
            }
            return true;
        }else if (id == R.id.action_quit){

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result retunr ", "on on on ");
    }

    private class GetContacts extends AsyncTask<ListView,Void,Void>{

        @Override
        protected Void doInBackground(ListView... listViews) {

            try {

                String ipServeur = "";
                Parametre p = CtrParams.getInstance().getParametre();
                if(p != null && !p.getIpAddress().isEmpty()){

                    ipServeur = p.getIpAddress();
                }
                String request ="http://"+ipServeur+"/contact?mail="+p.getPseudo();
                String res = JsonManager.readJsonFromUrl(request);
                JSONObject jsonObject  = new JSONObject(res);
                if(jsonObject != null){

                    if(jsonObject.getInt("_action") == Constants.UPDATE_CONTACT_LIST){//update contact list

                        JSONArray jarray = jsonObject.getJSONArray("_contacts");
                        String[] names = new String[jarray.length()];
                        if(jarray != null){

                            for (int i =0; i < jarray.length();i++){

                                JSONObject objC = new JSONObject(jarray.get(i).toString());
                                if(objC != null){

                                    if(objC.getInt("etat") == Constants.USER_CONNECTED){//contact connected

                                        names[i]= objC.getString("mail");
                                    }
                                }
                            }
                            ListView l = listViews[0];
                            if(l != null){

                                ContactListAdapter cla = new ContactListAdapter(HomeActivity.this, names);
                                l.setAdapter(cla);
                            }
                        }
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }
    }
}

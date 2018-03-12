package com.ludo.barel.clientmobandvocspring.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.controls.CtrParams;
import com.ludo.barel.clientmobandvocspring.models.Parametre;
import com.ludo.barel.clientmobandvocspring.utils.ButtonListener;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set events listener for buttons
        Button connexionBut = findViewById(R.id.Login);
        Button subscribeBut = findViewById(R.id.subscribe);

        connexionBut.setOnClickListener(new ButtonListener());
        subscribeBut.setOnClickListener(new ButtonListener());
        new BackgroundTask().execute();
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
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                intent.putExtra("ipAddress",p.getIpAddress());
                intent.putExtra("pseudo",p.getPseudo());
                intent.putExtra("notification",p.getNotificationPath());
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


    private class BackgroundTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            //set file path for configuration
            //at this point only path is specify no file create
            CtrParams.getInstance().setPathConfigFile(getBaseContext().getFilesDir().getAbsolutePath()+File.separator+"config.json");
            return  null;
        }
    }


}

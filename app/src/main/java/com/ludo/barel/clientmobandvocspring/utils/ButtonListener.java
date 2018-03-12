package com.ludo.barel.clientmobandvocspring.utils;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.activities.AddContactActivity;
import com.ludo.barel.clientmobandvocspring.activities.HomeActivity;
import com.ludo.barel.clientmobandvocspring.activities.MainActivity;
import com.ludo.barel.clientmobandvocspring.activities.SubscribeActivity;
import com.ludo.barel.clientmobandvocspring.controls.CtrLogin;
import com.ludo.barel.clientmobandvocspring.controls.CtrParams;
import com.ludo.barel.clientmobandvocspring.models.Parametre;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by iosdev on 27/02/2018.
 */

public class ButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.Login:

                Log.d("button listener ","Connexion button tapped");
                EditText _login = v.getRootView().findViewById(R.id.pseudo);
                EditText _pass = v.getRootView().findViewById(R.id.password);
                if(!_login.getText().toString().isEmpty() && !_pass.getText().toString().isEmpty()){

                    String loginTxt = _login.getText().toString();
                    String passTxt = _pass.getText().toString();
                    CtrLogin.getSharedInstance().connectUser(loginTxt, passTxt,v.getRootView(),false);
                }
                break;
            case R.id.subscribe:

               Intent intent =  new Intent(v.getContext(),SubscribeActivity.class);
               v.getContext().startActivity(intent);
                break;
            case R.id.saveParamsBut:

                Log.d("button listener ","save params button tapped");
                EditText _serveurAdd = v.getRootView().findViewById(R.id.serveurAdd);
                EditText _pseudo = v.getRootView().findViewById(R.id.pseudoParamsTxt);
                EditText _notification = v.getRootView().findViewById(R.id.soundPathParamsTxt);

                if(!_serveurAdd.getText().toString().matches(Constants.IP_REGEX_TXT)){//IP Address wrong format

                    Toast.makeText(v.getRootView().getContext(), "format address ip invalide", Toast.LENGTH_LONG).show();
                    return;
                }

                if(_notification != null && _pseudo != null && _serveurAdd != null){

                    try {

                        if(_serveurAdd.getText().toString().equals("127.0.0.1:8080")){

                            Toast.makeText(v.getRootView().getContext(), "127.0.0.1:8080 n'est pas une adresse valide", Toast.LENGTH_LONG).show();
                            return;
                        }
                        CtrParams.getInstance().setParametre(new Parametre(_serveurAdd.getText().toString(),_pseudo.getText().toString(),_notification.getText().toString()));
                        CtrParams.getInstance().createConfigFileWith();
                        Toast.makeText(v.getRootView().getContext(), "Modification enregistrer", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }else{

                    Toast.makeText(v.getRootView().getContext(), "Modification non enregistrer", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.contact:

                //Do this in order to force refresh when contact will be add
                Intent intent2 = new Intent(v.getContext(), AddContactActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent2);
                break;
            case R.id.msg:
                Log.d("buttonListener ","msg tapped");
                break;
            case R.id.validerSubscribe:

                Log.d("buttonListener ","valid subscribe tapped");
                CtrLogin.getSharedInstance().connectUser(null,null,v.getRootView(),true);
                break;
        }
    }
}

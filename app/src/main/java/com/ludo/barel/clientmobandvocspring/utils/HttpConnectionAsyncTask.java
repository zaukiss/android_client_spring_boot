package com.ludo.barel.clientmobandvocspring.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.ludo.barel.clientmobandvocspring.controls.CtrParams;
import com.ludo.barel.clientmobandvocspring.models.Parametre;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by iosdev on 05/03/2018.
 */

public class HttpConnectionAsyncTask extends AsyncTask<String[],Void,String> {

    //here we manage request for connect user i.e for subscribe and connect
    //the user. I've chosed to do all in one class because i thinks that is
    // the same thing when a user connect or subscribe

    public HttpConnectionAsyncTask() {

        super();
    }

    @Override
    protected String doInBackground(String[]... connexionInfos) {

        String[] arg =  connexionInfos[0];
        if(arg.length == 2) {

            //index 0 : login
            //index 1 : password
            String login = arg[0];
            String password = arg[1];
            try {

                String ipServeur = "";
                Parametre p = CtrParams.getInstance().getParametre();
                if(p != null && !p.getIpAddress().isEmpty()){

                    ipServeur = p.getIpAddress();
                }else{

                    return "no server ip";
                }
                String urlTxt = "http://" + ipServeur
                        + "/connect?name="
                        + login + "&password="
                        + password + "&ip=" + NetworkUtils.getLocalHostLANAddress().toString().replace("/", "");
                return JsonManager.readJsonFromUrl(urlTxt);
            } catch (Exception e) {

                return "Network is unreachable";
            }
        }else if(arg.length == 3){

            //index 0 : pseudo
            //index 1 : password
            //index 2 : mail

            String ipServeur = "";
            Parametre p = null;
            String pseudo = arg[0];
            String pass = arg[1];
            String mail = arg[2];
            try {

                p = CtrParams.getInstance().getParametre();
                if(p != null && !p.getIpAddress().isEmpty()){

                    ipServeur = p.getIpAddress();
                }else{

                    return "no server ip";
                }
                String urlTxt = "http://" + ipServeur
                        + "/subscribe?name="
                        + pseudo + "&password="  + pass
                        +"&mail="+mail
                        + "&ip=" + NetworkUtils.getLocalHostLANAddress().toString().replace("/", "");
                return JsonManager.readJsonFromUrl(urlTxt);

            } catch (Exception e) {

                return "Network is unreachable";
            }
        }
        return null;
    }
}

package com.ludo.barel.clientmobandvocspring.controls;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.activities.HomeActivity;
import com.ludo.barel.clientmobandvocspring.utils.Constants;
import com.ludo.barel.clientmobandvocspring.utils.HttpConnectionAsyncTask;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by iosdev on 27/02/2018.
 */

public class CtrLogin {

    private static volatile CtrLogin _shared = null;

    private CtrLogin(){}

    public static CtrLogin getSharedInstance(){

        synchronized (CtrLogin.class){

            if(_shared == null){

                _shared = new CtrLogin();
            }
            return  _shared;
        }
    }

    public void connectUser(String login, String password, View view,boolean subscribeBefor){

        //This method allow to manage subscribe or connection of a user.
        //According the case some process is executed. After a user
        //was subscribe a connection request is necessary this why
        //i've chosen to put both in the same method.

        if(subscribeBefor){//case user subscription

            //get all fields
            EditText pseudoField = view.getRootView().findViewById(R.id.pseudoSubscribeContent);
            EditText passwordField = view.getRootView().findViewById(R.id.passwordSubscribeContent);
            EditText mailField = view.getRootView().findViewById(R.id.mailSubscribeContent);
            EditText confirmPassField = view.getRootView().findViewById(R.id.passwordConfirm);

            //get all fields to string
            String pseudoTxt = pseudoField.getText().toString();
            String passwordTxt = passwordField.getText().toString();
            String mailTxt = mailField.getText().toString();
            String confirmPassTxt = confirmPassField.getText().toString();

            //check if no errors in fields
            if(!pseudoTxt.isEmpty() && !passwordTxt.isEmpty() && !mailTxt.isEmpty() && !confirmPassTxt.isEmpty()){

                if(!mailTxt.matches(Constants.REGEX_MAIL)){

                    Toast.makeText(view.getContext(), "champs mail invalide", Toast.LENGTH_LONG).show();
                    return;
                }else if(!confirmPassTxt.equals(confirmPassTxt)){

                    Toast.makeText(view.getContext(), "les passwords ne sont pas identiques", Toast.LENGTH_LONG).show();
                    return;
                }else{

                    //no error at fields level
                    String[] conInfs = new String[3];
                    conInfs[0] = pseudoTxt;
                    conInfs[1] = passwordTxt;
                    conInfs[2] = mailTxt;
                    try {

                        String result = new HttpConnectionAsyncTask().execute(conInfs).get();//process request in async background task
                        //check server errors
                        if (result != null && result.equals("no server ip")) {

                            Toast.makeText(view.getContext(), "Configurer l'adresse du serveur d'abord", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if (result != null && result.equals("Network is unreachable")) {

                            Toast.makeText(view.getContext(), "Serveur inaccessible verifier votre connexion réseau", Toast.LENGTH_LONG).show();
                            return;
                        }else if (result == null) {

                            Toast.makeText(view.getContext(), "Erreur interne", Toast.LENGTH_LONG).show();
                            return;
                        }

                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject != null && jsonObject.getInt("_status") == Constants.SUBSCRIBE_SUCCESS) {

                            //start the new Activity
                            Toast.makeText(view.getContext(), "Connexion réussie", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(view.getContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            view.getContext().startActivity(intent);
                        }else if (jsonObject != null && jsonObject.getInt("_status") == Constants.USER_ALREADY_EXIST) {

                            Toast.makeText(view.getContext(), "l'utilisateur existe déjà ( changer votre email et pseudo )", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (Exception e) {

                        Toast.makeText(view.getContext(), "Erreur interne", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }else{

                Toast.makeText(view.getContext(), "tous les champs doivent être remplis", Toast.LENGTH_LONG).show();
                return;
            }
        }else {// case user connection

            if (!login.matches(Constants.REGEX_MAIL)) {//force user to give his mail as login for connection

                //Reason :
                //in serveur side, for now, i don't work with pseudo i'm suppose pseudo value
                //is equal to mail value. this way, user are unique ( no duplicate user ).
                //This allow to ensure the integrity of database.
                Toast.makeText(view.getContext(), "la syntax de votre email est invalide", Toast.LENGTH_LONG).show();
                return;
            }

            String[] conInfs = new String[2];
            conInfs[0] = login;
            conInfs[1] = password;
            EditText edTLogin = view.findViewById(R.id.pseudo);
            EditText edTpassword = view.findViewById(R.id.password);

            edTLogin.setBackgroundColor(Color.parseColor("#bac8cd"));
            edTpassword.setBackgroundColor(Color.parseColor("#bac8cd"));
            try {

                String result = new HttpConnectionAsyncTask().execute(conInfs).get();
                if (result != null && result.equals("no server ip")) {

                    Toast.makeText(view.getContext(), "Configurer l'adresse du serveur d'abord", Toast.LENGTH_LONG).show();
                    return;
                } else if (result != null && result.equals("Network is unreachable")) {

                    Toast.makeText(view.getContext(), "Serveur inaccessible verifier votre connexion réseau", Toast.LENGTH_LONG).show();
                    return;
                } else if (result == null) {

                    Toast.makeText(view.getContext(), "Erreur interne", Toast.LENGTH_LONG).show();
                    return;
                }
                
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject != null && jsonObject.getInt("_status") == Constants.CONNEXION_SUCCESS) {


                    //start the new Activity
                    Toast.makeText(view.getContext(), "Connexion réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    view.getContext().startActivity(intent);
                } else if (jsonObject != null && jsonObject.getInt("_status") == Constants.USER_NOT_FOUND) {

                    Toast.makeText(view.getContext(), "Utilisateur inconnue ", Toast.LENGTH_LONG).show();
                    edTLogin.setBackgroundColor(Color.RED);
                } else if (jsonObject != null && jsonObject.getInt("_status") == Constants.USER_BAD_PASSWORD) {

                    Toast.makeText(view.getContext(), "Mauvais mot de passe", Toast.LENGTH_LONG).show();
                    edTpassword.setBackgroundColor(Color.RED);
                } else {

                    Toast.makeText(view.getContext(), "Erreur serveur", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

                Toast.makeText(view.getContext(), "Erreur interne", Toast.LENGTH_LONG).show();
            }
        }
    }
}

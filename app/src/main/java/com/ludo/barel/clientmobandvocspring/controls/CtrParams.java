package com.ludo.barel.clientmobandvocspring.controls;

import android.util.Log;

import com.ludo.barel.clientmobandvocspring.models.Parametre;
import com.ludo.barel.clientmobandvocspring.utils.JsonManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

/**
 * Created by iosdev on 28/02/2018.
 */

public class CtrParams {

    private JSONObject _confObject ;
    private volatile static CtrParams _sharedInstance = null;//keyword volatile ensure that the shared instance have the right value when we use it
    private String _pathConfigFile ;

    private CtrParams(){ }
    public static CtrParams getInstance(){

        synchronized (CtrParams.class) {//protect access to the shared instance

            if(_sharedInstance == null){

                _sharedInstance = new CtrParams();
            }
            return _sharedInstance;
        }
    }

    public void createConfigFileWith() throws JSONException, IOException {

        Log.d("createConfigFileWith",this._confObject+"");
        if(this._confObject != null &&!this._pathConfigFile.isEmpty()){

            JsonManager.save(this._confObject, this._pathConfigFile);
        }else{

            Log.d("createConfigFileWith", "save failed");
        }
    }

    public String getPathConfigFile() {

        return this._pathConfigFile;
    }

    public void setPathConfigFile(String _pathConfigFile) {

        this._pathConfigFile = _pathConfigFile;
        Log.d("configFilePath",this._pathConfigFile);
    }

    public void setParametre(Parametre p) throws JSONException {

        this._confObject = new JSONObject();
        if(p != null && this._confObject != null){

            this._confObject.put("ipAddress",(p.getIpAddress() != null ? p.getIpAddress() : ""));
            this._confObject.put("pseudo",(p.getPseudo() != null ? p.getPseudo() : ""));
            this._confObject.put("notifPath",(p.getNotificationPath() != null ? p.getNotificationPath() : ""));
        }
    }

    public Parametre getParametre() throws JSONException, IOException, ClassNotFoundException {

        Parametre res = null;
        this._confObject = JsonManager.restoreFrom(this._pathConfigFile);
        if(this._confObject != null){

            res = new Parametre(this._confObject.getString("ipAddress"),this._confObject.getString("pseudo"),this._confObject.getString("notifPath"));
        }
        return  res;

    }
}

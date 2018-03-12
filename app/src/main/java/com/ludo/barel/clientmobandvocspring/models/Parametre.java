package com.ludo.barel.clientmobandvocspring.models;

import java.io.Serializable;

/**
 * Created by iosdev on 28/02/2018.
 */

public class Parametre{

    private String _ipAddress = null;
    private String _pseudo = null;
    private String _notificationPath =null;

    public Parametre(String ip, String pseudo, String notificationPath){

        this._ipAddress =  ip;
        this._pseudo = pseudo;
        this._notificationPath = notificationPath;
    }

    public String getIpAddress() {

        return _ipAddress;
    }

    public void setIpAddress(String _ipAddress) {

        this._ipAddress = _ipAddress;
    }

    public String getPseudo() {

        return _pseudo;
    }

    public void setPseudo(String _pseudo) {

        this._pseudo = _pseudo;
    }

    public String getNotificationPath() {

        return _notificationPath;
    }

    public void setNotificationPath(String _notificationPath) {

        this._notificationPath = _notificationPath;
    }
}

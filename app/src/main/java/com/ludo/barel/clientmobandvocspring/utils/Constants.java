package com.ludo.barel.clientmobandvocspring.utils;

/**
 * Created by iosdev on 27/02/2018.
 */

public class Constants {

    public static final String IP_REGEX_TXT = "[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}:[0-9]+";
    public static String SERVER_ADDRESS = "192.168.0.90";
    public static final String REGEX_MAIL = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";

    public static  final int USER_CONNECTED = 2;

    public static final int SUBSCRIBE_SUCCESS = 200;
    public static  final  int CONNEXION_SUCCESS = 200;

    public static final int UPDATE_CONTACT_LIST = 204;

    public static  final  int USER_NOT_FOUND = 404;
    public static  final  int USER_BAD_PASSWORD= 486;
    public static final int USER_ALREADY_EXIST = 487;

}

package com.ludo.barel.clientmobandvocspring.utils;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by iosdev on 28/02/2018.
 */

public class JsonManager {

    public static  void save(JSONObject object, String path) throws IOException {

        File data = new File(path);
        if (!data.createNewFile()) {

            data.delete();
            data.createNewFile();
        }
        Writer output;
        File file = new File(path);
        output = new BufferedWriter(new FileWriter(file));
        output.write(object.toString());
        output.close();
    }

    public static JSONObject restoreFrom(String path) throws IOException, ClassNotFoundException, JSONException {

        JSONObject object = null;
        File data = new File(path);
        StringBuilder text = new StringBuilder();
        if(data.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(data));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            object = new JSONObject(text.toString());
        }
        return object;
    }

    public static String readJsonFromUrl(String urlTxt) throws IOException {


        URL url = new URL(urlTxt);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int status =  connection.getResponseCode();
        if (status ==  200) {

            InputStream it = new BufferedInputStream(connection.getInputStream());
            InputStreamReader read = new InputStreamReader(it);
            BufferedReader buff = new BufferedReader(read);
            StringBuilder dta = new StringBuilder();
            String chunks ;
            while((chunks = buff.readLine()) != null)
            {

                dta.append(chunks);
            }
            return  dta.toString();
        }
        return null;
    }
}

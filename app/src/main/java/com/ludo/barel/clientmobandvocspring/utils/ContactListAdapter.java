package com.ludo.barel.clientmobandvocspring.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ludo.barel.clientmobandvocspring.R;

/**
 * Created by iosdev on 09/03/2018.
 */

public class ContactListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] names;

    public ContactListAdapter(Activity context, String[] nameArrayParam) {

        super(context, R.layout.list_row , nameArrayParam);
        this.context = context;
        this.names = nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        //gets references to objects in the list_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.contactName);

        //sets the values of the objects to values from the arrays
        nameTextField.setText(names[position]);
        return rowView;
    }
}

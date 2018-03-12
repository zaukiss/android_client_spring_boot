package com.ludo.barel.clientmobandvocspring.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.utils.ButtonListener;

public class SubscribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Button validerBut =  findViewById(R.id.validerSubscribe);
        validerBut.setOnClickListener(new ButtonListener());
    }
}

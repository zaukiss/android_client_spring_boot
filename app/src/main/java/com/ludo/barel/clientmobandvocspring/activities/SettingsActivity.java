package com.ludo.barel.clientmobandvocspring.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ludo.barel.clientmobandvocspring.R;
import com.ludo.barel.clientmobandvocspring.utils.ButtonListener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button saveParamsBut = findViewById(R.id.saveParamsBut);
        saveParamsBut.setOnClickListener(new ButtonListener());

        EditText _serveurAdd = findViewById(R.id.serveurAdd);
        EditText _pseudo = findViewById(R.id.pseudoParamsTxt);
        EditText _notification = findViewById(R.id.soundPathParamsTxt);

        Bundle extras =  getIntent().getExtras();
        if(extras != null && !extras.isEmpty()){

            _serveurAdd.setText(extras.getString("ipAddress"), TextView.BufferType.EDITABLE);
            _pseudo.setText(extras.getString("pseudo"), TextView.BufferType.EDITABLE);
            _notification.setText(extras.getString("notification"), TextView.BufferType.EDITABLE);
        }else{

            _serveurAdd.setText("", TextView.BufferType.EDITABLE);
            _pseudo.setText("", TextView.BufferType.EDITABLE);
            _notification.setText("", TextView.BufferType.EDITABLE);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

package com.rootlol.chatrootlol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ETlogin;

    public static final String APP_PREFERENCES = "LoginChat";
    public static final String APP_PREFERENCES_COUNTER = "login";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETlogin = findViewById(R.id.ETlogin);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            String mCounter = mSettings.getString(APP_PREFERENCES_COUNTER,"error");
            if (!mCounter.equals("error") & !mCounter.equals("exit") & !mCounter.equals("")){
                startAc(mCounter);
            }
        }
    }

    public void startChat(View view){
        if (!ETlogin.getText().toString().equals("") & !ETlogin.getText().toString().equals("error") & !ETlogin.getText().toString().equals("exit")) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_COUNTER, ETlogin.getText().toString());
            editor.apply();
            startAc(ETlogin.getText().toString());
        } else {
            ETlogin.setText("");
            Toast.makeText(getApplicationContext(),"Login error",Toast.LENGTH_SHORT).show();
        }
    }

    public void startAc(String login){
        Intent intent1 = new Intent(MainActivity.this, ChatActivity.class);
        intent1.putExtra(APP_PREFERENCES_COUNTER, login);
        startActivity(intent1);
    }
}
package com.rootlol.chatrootlol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.rootlol.chatrootlol.adapter.MessBody;
import com.rootlol.chatrootlol.objMess.bodyMess;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText ET1;
    String login;
    ListView lvMain;
    int stringLen = 0;
    SharedPreferences mSettings;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.9:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Api messagesApi = retrofit.create(Api.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ET1 = findViewById(R.id.et);
        login = getIntent().getExtras().get("login").toString();
        lvMain = (ListView) findViewById(R.id.lv);
        mSettings = getSharedPreferences("LoginChat", Context.MODE_PRIVATE);
        Call<List<bodyMess>> messages = messagesApi.messages();
        messages.enqueue(new Callback<List<bodyMess>>() {
                        @Override
                        public void onResponse(Call<List<bodyMess>> call, Response<List<bodyMess>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "404", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (stringLen != response.body().size()) {
                                List<bodyMess> mess = response.body();
                                MessBody adapter = new MessBody(ChatActivity.this, mess);
                                lvMain.setAdapter(adapter);
                                Toast.makeText(getApplicationContext(), Integer.toString(response.body().size()), Toast.LENGTH_SHORT).show();
                                stringLen = response.body().size();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<bodyMess>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exemple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Call<List<bodyMess>> messages = messagesApi.messages();
                messages.enqueue(new Callback<List<bodyMess>>() {
                    @Override
                    public void onResponse(Call<List<bodyMess>> call, Response<List<bodyMess>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "404", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (stringLen != response.body().size()) {
                            List<bodyMess> mess = response.body();
                            MessBody adapter = new MessBody(ChatActivity.this, mess);
                            lvMain.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), Integer.toString(response.body().size()), Toast.LENGTH_SHORT).show();
                            stringLen = response.body().size();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<bodyMess>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void exitCh(View view){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("login", "exit");
        editor.apply();
        Intent intent1 = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent1);
    }

    public void sendMess(View view){
        class SendTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... path) {

                String content;
                try {
                    content = getContent(path[0]);
                } catch (IOException ex) {
                    content = ex.getMessage();
                }
                return content;
            }

            @Override
            protected void onPostExecute(String content) {
                if (!("failed".equals(content.split(" ")[0]))) {
                    ET1.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "network error", Toast.LENGTH_SHORT).show();
                }
            }

            private String getContent(String path) throws IOException {
                BufferedReader reader=null;
                try {
                    URL url=new URL(path);
                    HttpURLConnection c=(HttpURLConnection)url.openConnection();
                    c.setRequestMethod("GET");
                    c.setReadTimeout(10000);
                    c.connect();
                    reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder buf=new StringBuilder();
                    String line=null;
                    while ((line=reader.readLine()) != null) {
                        buf.append(line + "\n");
                    }
                    return(buf.toString());
                }
                finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        }
        if (!ET1.getText().toString().equals("")) {
            new SendTask().execute("http://192.168.1.9:8080/api?login=" + login + "&mess=" + ET1.getText());
        } else {
            Toast.makeText(this, "not write text message", Toast.LENGTH_SHORT).show();
        }
    }

}

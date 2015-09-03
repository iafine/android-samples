package com.hyyy.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText editUsername;
    private EditText editPassword;
    private Button btnWrite;
    private Button btnRead;
    private TextView showUsername;
    private TextView showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intitView();

        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void intitView() {
        editUsername = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);
        showUsername = (TextView) findViewById(R.id.show_username);
        showPassword = (TextView) findViewById(R.id.show_password);
        btnRead = (Button) findViewById(R.id.write);
        btnRead.setOnClickListener(this);
        btnWrite = (Button) findViewById(R.id.read);
        btnWrite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write:
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if ("".equals(username) || "".equals(password)) {
                    Toast.makeText(MainActivity.this, "Please input username or password!", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    Toast.makeText(MainActivity.this, "Write Successfully!", Toast.LENGTH_SHORT).show();
                }

                editor.commit();
                break;

            case R.id.read:
                String name = sharedPreferences.getString("username", null);
                String pass = sharedPreferences.getString("password", null);
                if ("".equals(name) || "".equals(pass)) {
                    Toast.makeText(MainActivity.this, "The user.xml is null,PLease write it!", Toast.LENGTH_SHORT).show();
                } else {
                    showUsername.setText(name);
                    showPassword.setText(pass);
                }
        }
    }
}

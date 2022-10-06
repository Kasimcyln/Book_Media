package com.kasimkartal866.mybookmedia.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kasimkartal866.mybookmedia.common.App;
import com.kasimkartal866.mybookmedia.common.MyPref;
import com.kasimkartal866.mybookmedia.R;
import com.kasimkartal866.mybookmedia.db.User;
import com.kasimkartal866.mybookmedia.db.UserDao;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView textViewRegister;
    UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.btnLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        dao = App.dao;
        onClickMethods();

    }

    public void onClickMethods() {
        btnLogin.setOnClickListener(v -> {

            String emailText = email.getText().toString();
            String passwordText = pass.getText().toString();

            Boolean state = false;
            if (TextUtils.isEmpty(email.getText().toString())) {
                email.setError("enter e-mail");
                state = true;
            } if (TextUtils.isEmpty(pass.getText().toString())) {
                pass.setError("enter password");
                state = true;
            }
            else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        User user = dao.checkUserPass(emailText, passwordText);
                        if(user == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Geçersiz Giriş Bilgileri",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            String name = user.getEmail();
                            startActivity(new Intent(LoginActivity.this,MainPageActivity.class)
                                    .putExtra("name",name));

                            MyPref.getInstance().saveUserName(name);
                            MyPref.getInstance().saveUserId(user.getId());
                            finish();
                        }
                    }
                }).start();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
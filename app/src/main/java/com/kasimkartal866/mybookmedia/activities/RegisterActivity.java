package com.kasimkartal866.mybookmedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kasimkartal866.mybookmedia.common.App;
import com.kasimkartal866.mybookmedia.R;
import com.kasimkartal866.mybookmedia.db.User;
import com.kasimkartal866.mybookmedia.db.UserDao;
import com.kasimkartal866.mybookmedia.common.Utilities;

public class RegisterActivity extends AppCompatActivity {

    private EditText etMail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etPassword2;
    private Button btnSave;
    UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dao = App.dao;
        findViewByIdMethods();
        onClickMethods();
    }

    public void findViewByIdMethods() {
        etMail = findViewById(R.id.etMail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        btnSave = findViewById(R.id.btnSave);
    }

    public void onClickMethods () {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean state = false;
                if (TextUtils.isEmpty(etMail.getText().toString()) ) {
                    etMail.setError("enter e-mail");
                    state = true;
                }
                if (!Utilities.isValidEmail(etMail.getText().toString()) ) {
                    etMail.setError("enter .gmail.com");
                    state = true;
                }
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    etPhone.setError("enter phone");
                    state = true;
                }

                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError("enter password");
                    state = true;
                }
                if (!Utilities.isValidPassword(etPassword.getText().toString())) {
                    etPassword.setError("büyük harf, küçük harf, rakam ve noktalama işareti içermelidir");
                    state = true;
                }
                if (TextUtils.isEmpty(etPassword2.getText().toString())) {
                    etPassword2.setError("try enter password");
                    state = true;
                } else if (!etPassword2.getText().toString().contentEquals(etPassword.getText().toString())) {
                    etPassword2.setError("passwords are incompatible");
                    state = true;
                }
                if (state){
                    return;
                }

                User user = new User();
                user.setEmail(etMail.getText().toString());
                user.setPhone(etPhone.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setPassword2(etPassword2.getText().toString());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dao.addUser(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
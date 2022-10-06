package com.kasimkartal866.mybookmedia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.kasimkartal866.mybookmedia.common.G;
import com.kasimkartal866.mybookmedia.common.MyPref;
import com.kasimkartal866.mybookmedia.R;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo = findViewById(R.id.imageView2);
        Thread thread = new Thread() {
            @Override
            public void run () {
                try {

                    sleep(3000);
                }catch (Exception e) {

                    e.printStackTrace();
                }finally {

                    String name = MyPref.getInstance().getUserName();
                    if(name.equals(""))
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }else
                    {
                        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(G.USER_NAME_INTENT_KEY, name);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();
    }
}
package com.sarahehabm.carbcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.sarahehabm.carbcalculator.common.Preferences;
import com.sarahehabm.carbcalculator.login.LoginActivity;
import com.sarahehabm.carbcalculator.main.view.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Preferences preferences = new Preferences(this);
        String username = preferences.getString(Preferences.KEY_NAME);
        final boolean isUserLoggedIn = (username == null || username.isEmpty())? false : true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(isUserLoggedIn)
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                else
                    intent = new Intent(SplashActivity.this, LoginActivity.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

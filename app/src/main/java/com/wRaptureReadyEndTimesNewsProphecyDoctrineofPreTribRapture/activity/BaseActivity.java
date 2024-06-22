package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ThemeModel;

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        //setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Integer colorPosition = preferences.getInt(ThemeModel.COLOR_KEY, 0);
        ThemeModel themeModel = ThemeModel.get(colorPosition);

        getWindow().setStatusBarColor(themeModel.STATUS_BAR_COLOR);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }


        listener = (sharedPreferences, key) -> {
            assert key != null;
            if (key.equals(ThemeModel.COLOR_KEY)) {
                Integer position = sharedPreferences.getInt(ThemeModel.COLOR_KEY, 0);
                ThemeModel themeModel1 = ThemeModel.get(position);

                getWindow().setStatusBarColor(themeModel1.STATUS_BAR_COLOR);
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }
}

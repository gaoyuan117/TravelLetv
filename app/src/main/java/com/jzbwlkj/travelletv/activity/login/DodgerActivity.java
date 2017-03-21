package com.jzbwlkj.travelletv.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.service.LocationService;

import java.util.Timer;
import java.util.TimerTask;

public class DodgerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodger);
        //启动定位
        startService(new Intent(this, LocationService.class));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplication(),LoginActivity.class));
                finish();
            }
        },3000);
    }
}

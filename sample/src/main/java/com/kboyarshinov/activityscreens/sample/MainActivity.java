package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class MainActivity extends ActionBarActivity {

    @ActivityArg
    int field;
    @ActivityArg
    float field2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

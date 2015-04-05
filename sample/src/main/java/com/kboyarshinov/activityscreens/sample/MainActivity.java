package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class MainActivity extends ActionBarActivity {

//    @ActivityArg
//    int field;
//    @ActivityArg(required = false)
//    float field2;

    @ActivityArg
    int fieldInt;
    @ActivityArg
    float fieldFloat;
    @ActivityArg(required = false)
    double fieldDouble;
    @ActivityArg
    byte fieldByte;
    @ActivityArg(required = false)
    boolean fieldBoolean;
    @ActivityArg
    char fieldChar;
    @ActivityArg(required = false)
    short fieldShort;
    @ActivityArg
    long fieldLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

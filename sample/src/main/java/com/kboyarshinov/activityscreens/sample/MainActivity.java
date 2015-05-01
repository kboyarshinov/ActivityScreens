package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import com.kboyarshinov.activityscreens.sample.model.ParcelableClass;

@ActivityScreen
public class MainActivity extends ActionBarActivity {

    @ActivityArg
    String field1;
    @ActivityArg
    CharSequence field2;
    @ActivityArg
    CharSequence[] field3;
    @ActivityArg
    Bundle field4;
    @ActivityArg
    Parcelable field5;
    @ActivityArg
    Parcelable[] field6;
    @ActivityArg
    ParcelableClass field7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

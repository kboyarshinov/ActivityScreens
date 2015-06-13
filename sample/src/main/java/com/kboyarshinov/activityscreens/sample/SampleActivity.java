package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import com.kboyarshinov.activityscreens.sample.model.ParcelableClass;

@ActivityScreen
public class SampleActivity extends ActionBarActivity {

    @ActivityArg
    long id;
    @ActivityArg
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SampleActivityScreen.inject(this);
    }
}

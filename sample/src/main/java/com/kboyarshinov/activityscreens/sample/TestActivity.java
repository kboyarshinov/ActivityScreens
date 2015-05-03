package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import com.kboyarshinov.activityscreens.sample.model.ParcelableClass;

@ActivityScreen
public class TestActivity extends ActionBarActivity {

    @ActivityArg
    ParcelableClass[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestActivityScreen.inject(this);
        assert array != null;
    }
}

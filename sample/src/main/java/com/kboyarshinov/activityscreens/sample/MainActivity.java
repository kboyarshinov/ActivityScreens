package com.kboyarshinov.activityscreens.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.kboyarshinov.activityscreens.sample.model.ParcelableClass;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_test_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SampleActivityScreen(3, "Sample title").open(MainActivity.this);
            }
        });
    }
}

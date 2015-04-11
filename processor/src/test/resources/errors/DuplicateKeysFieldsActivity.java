package errors;

import android.app.Activity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class EmptyActivity extends Activity {
    @ActivityArg
    final int field;
    @ActivityArg(key = "field")
    final int field2;
}
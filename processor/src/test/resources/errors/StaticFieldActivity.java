package errors;

import android.app.Activity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class EmptyActivity extends Activity {
    @ActivityArg
    static int field;
}
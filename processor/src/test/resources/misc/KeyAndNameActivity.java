package misc;

import android.app.Activity;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class KeyAndNameActivity extends Activity {
    @ActivityArg(key = "key")
    String name;
}
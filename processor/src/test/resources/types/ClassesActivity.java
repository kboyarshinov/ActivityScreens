package types;

import android.app.Activity;
import android.os.Bundle;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class ClassesActivity extends Activity {
    @ActivityArg
    String field1;
    @ActivityArg
    CharSequence field2;
    @ActivityArg
    CharSequence[] field3;
    @ActivityArg
    Bundle field4;
}
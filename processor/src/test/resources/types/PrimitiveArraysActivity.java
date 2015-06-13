package types;

import android.app.Activity;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class PrimitiveArraysActivity extends Activity {
    @ActivityArg
    int array1[];
    @ActivityArg
    float array2[];
    @ActivityArg
    double array3[];
    @ActivityArg
    byte array4[];
    @ActivityArg
    boolean array5[];
    @ActivityArg
    char array6[];
    @ActivityArg
    short array7[];
    @ActivityArg
    long array8[];
}
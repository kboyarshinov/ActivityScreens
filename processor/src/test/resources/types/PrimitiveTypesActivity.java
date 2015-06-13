package types;

import android.app.Activity;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class PrimitiveTypesActivity extends Activity {
    @ActivityArg
    int field1;
    @ActivityArg
    float field2;
    @ActivityArg
    double field3;
    @ActivityArg
    byte field4;
    @ActivityArg
    boolean field5;
    @ActivityArg
    char field6;
    @ActivityArg
    short field7;
    @ActivityArg
    long field8;
}
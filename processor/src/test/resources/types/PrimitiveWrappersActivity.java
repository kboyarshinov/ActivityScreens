package types;

import android.app.Activity;
import android.os.Bundle;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class PrimitiveWrappersActivity extends Activity {
    @ActivityArg
    Character field1;
    @ActivityArg
    Integer field2;
    @ActivityArg
    Double field3;
    @ActivityArg
    Float field4;
    @ActivityArg
    Boolean field5;
    @ActivityArg
    Short field6;
    @ActivityArg
    Byte field7;
    @ActivityArg
    Long field8;
}
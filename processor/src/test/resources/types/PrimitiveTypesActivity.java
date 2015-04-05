package types;

import android.app.Activity;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

@ActivityScreen
public class PrimitiveTypesActivity extends Activity {
    @ActivityArg
    int fieldInt;
    @ActivityArg
    float fieldFloat;
    @ActivityArg
    double fieldDouble;
    @ActivityArg
    byte fieldByte;
    @ActivityArg
    boolean fieldBoolean;
    @ActivityArg
    char fieldChar;
    @ActivityArg
    short fieldShort;
    @ActivityArg
    long fieldLong;
}
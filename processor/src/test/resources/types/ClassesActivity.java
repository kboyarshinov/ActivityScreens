package types;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import model.ParcelableClass;

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
    @ActivityArg
    Parcelable field5;
    @ActivityArg
    Parcelable[] field6;
    @ActivityArg
    ParcelableClass field7;
}
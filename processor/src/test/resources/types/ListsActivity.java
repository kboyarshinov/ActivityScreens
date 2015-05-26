package types;

import android.app.Activity;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;

import java.util.ArrayList;

@ActivityScreen
public class ListsActivity extends Activity {
    @ActivityArg
    ArrayList<String> list1;
    @ActivityArg
    ArrayList<Integer> list2;
    @ActivityArg
    ArrayList<CharSequence> list3;
}
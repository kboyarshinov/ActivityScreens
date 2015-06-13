package types;

import android.app.Activity;
import android.os.Parcelable;
import android.util.SparseArray;
import com.kboyarshinov.activityscreens.annotation.ActivityArg;
import com.kboyarshinov.activityscreens.annotation.ActivityScreen;
import model.ParcelableClass;

import java.util.ArrayList;

@ActivityScreen
public class ListsActivity extends Activity {
    @ActivityArg
    ArrayList<String> list1;
    @ActivityArg
    ArrayList<Integer> list2;
    @ActivityArg
    ArrayList<CharSequence> list3;
    @ActivityArg
    ArrayList<Parcelable> list4;
    @ActivityArg
    ArrayList<ParcelableClass> list5;
    @ActivityArg
    SparseArray<Parcelable> list6;
    @ActivityArg
    SparseArray<ParcelableClass> list7;
}
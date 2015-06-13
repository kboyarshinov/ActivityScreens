package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.ArrayList;

import model.ParcelableClass;

public final class ListsActivityScreen {

    public final ArrayList<String> list1;
    public final ArrayList<Integer> list2;
    public final ArrayList<CharSequence> list3;
    public final ArrayList<Parcelable> list4;
    public final ArrayList<ParcelableClass> list5;
    public final SparseArray<Parcelable> list6;
    public final SparseArray<ParcelableClass> list7;

    public ListsActivityScreen(ArrayList<String> list1, ArrayList<Integer> list2, ArrayList<CharSequence> list3, ArrayList<Parcelable> list4, ArrayList<ParcelableClass> list5, SparseArray<Parcelable> list6, SparseArray<ParcelableClass> list7) {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;
        this.list5 = list5;
        this.list6 = list6;
        this.list7 = list7;
    }

    public void open(Activity activity) {
        Intent intent = toIntent(activity);
        activity.startActivity(intent);
    }

    public void openForResult(Activity activity, int requestCode) {
        Intent intent = toIntent(activity);
        activity.startActivityForResult(intent, requestCode);
    }

    public Intent toIntent(Activity activity) {
        Intent intent = new Intent(activity, ListsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list1", list1);
        bundle.putIntegerArrayList("list2", list2);
        bundle.putCharSequenceArrayList("list3", list3);
        bundle.putParcelableArrayList("list4", list4);
        bundle.putParcelableArrayList("list5", list5);
        bundle.putSparseParcelableArray("list6", list6);
        bundle.putSparseParcelableArray("list7", list7);
        intent.putExtras(bundle);
        return intent;
    }

    public static void inject(ListsActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("ListsActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
        activity.list1 = bundle.getStringArrayList("list1");
        activity.list2 = bundle.getIntegerArrayList("list2");
        activity.list3 = bundle.getCharSequenceArrayList("list3");
        activity.list4 = bundle.getParcelableArrayList("list4");
        activity.list5 = bundle.getParcelableArrayList("list5");
        activity.list6 = bundle.getSparseParcelableArray("list6");
        activity.list7 = bundle.getSparseParcelableArray("list7");
    }

    private static void checkArguments(Bundle bundle) {
        if (!bundle.containsKey("list1")) {
            throw new IllegalStateException("Required argument list1 with key 'list1' is not set");
        }
        if (!bundle.containsKey("list2")) {
            throw new IllegalStateException("Required argument list2 with key 'list2' is not set");
        }
        if (!bundle.containsKey("list3")) {
            throw new IllegalStateException("Required argument list3 with key 'list3' is not set");
        }
        if (!bundle.containsKey("list4")) {
            throw new IllegalStateException("Required argument list4 with key 'list4' is not set");
        }
        if (!bundle.containsKey("list5")) {
            throw new IllegalStateException("Required argument list5 with key 'list5' is not set");
        }
        if (!bundle.containsKey("list6")) {
            throw new IllegalStateException("Required argument list6 with key 'list6' is not set");
        }
        if (!bundle.containsKey("list7")) {
            throw new IllegalStateException("Required argument list7 with key 'list7' is not set");
        }
    }
}
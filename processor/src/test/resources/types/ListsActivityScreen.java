package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.ArrayList;

public final class ListsActivityScreen {

    public final ArrayList<String> list1;
    public final ArrayList<Integer> list2;
    public final ArrayList<CharSequence> list3;

    public ListsActivityScreen(ArrayList<String> list1, ArrayList<Integer> list2, ArrayList<CharSequence> list3) {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
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
        intent.putStringArrayListExtra("list1", list1);
        intent.putIntegerArrayListExtra("list2", list2);
        intent.putCharSequenceArrayListExtra("list3", list3);
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
    }
}
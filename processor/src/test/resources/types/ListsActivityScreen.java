package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.IllegalStateException;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.ArrayList;

public final class ListsActivityScreen {

    public final ArrayList<String> list1;

    public ListsActivityScreen(ArrayList<String> list1) {
        this.list1 = list1;
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
        return intent;
    }

    public static void inject(ListsActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("ListsActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
        activity.list1 = bundle.getStringArrayList("list1");
    }

    private static void checkArguments(Bundle bundle) {
        if (!bundle.containsKey("list1")) {
            throw new IllegalStateException("Required argument list1 with key 'list1' is not set");
        }
    }
}
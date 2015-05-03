package misc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.lang.NullPointerException;

public final class KeyAndNameActivityScreen {
    public final int name;

    public KeyAndNameActivityScreen(int name) {
        this.name = name;
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
        Intent intent = new Intent(activity, KeyAndNameActivity.class);
        intent.putExtra("key", name);
        return intent;
    }

    public static void inject(KeyAndNameActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("KeyAndNameActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        activity.name = bundle.getInt("key");
    }
}
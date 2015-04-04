package types;

import android.app.Activity;
import android.content.Intent;

public final class EmptyActivityScreen {
    private EmptyActivityScreen() {
    }

    public static void open(Activity activity) {
        Intent intent = createIntent(activity);
        activity.startActivity(intent);
    }

    public static void openForResult(Activity activity, int requestCode) {
        Intent intent = createIntent(activity);
        activity.startActivityForResult(intent, requestCode);
    }

    public static Intent createIntent(Activity activity) {
        Intent intent = new Intent(activity, EmptyActivity.class);
        return intent;
    }
}
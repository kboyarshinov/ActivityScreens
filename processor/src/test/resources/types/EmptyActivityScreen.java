package types;

import android.app.Activity;
import android.content.Intent;

public final class EmptyActivityScreen {
    public EmptyActivityScreen() {
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
        Intent intent = new Intent(activity, EmptyActivity.class);
        return intent;
    }
}
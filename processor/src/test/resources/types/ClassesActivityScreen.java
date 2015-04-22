package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.CharSequence;
import java.lang.String;

public final class ClassesActivityScreen {
    public final String field1;
    public final CharSequence field2;
    public final CharSequence[] field3;
    public final Bundle field4;

    public ClassesActivityScreen(String field1, CharSequence field2, CharSequence[] field3, Bundle field4) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
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
        Intent intent = new Intent(activity, ClassesActivity.class);
        intent.putExtra("field1", field1);
        intent.putExtra("field2", field2);
        intent.putExtra("field3", field3);
        intent.putExtra("field4", field4);
        return intent;
    }

    public static void inject(ClassesActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        activity.field1 = bundle.getString("field1");
        activity.field2 = bundle.getCharSequence("field2");
        activity.field3 = bundle.getCharSequenceArray("field3");
        activity.field4 = bundle.getBundle("field4");
    }
}
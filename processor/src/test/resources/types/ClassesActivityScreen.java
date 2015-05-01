package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.lang.CharSequence;
import java.lang.NullPointerException;
import java.lang.String;
import model.ParcelableClass;

public final class ClassesActivityScreen {
    public final String field1;
    public final CharSequence field2;
    public final CharSequence[] field3;
    public final Bundle field4;
    public final Parcelable field5;
    public final Parcelable[] field6;
    public final ParcelableClass field7;
    public final String[] field8;
    public final ParcelableClass[] field9;

    public ClassesActivityScreen(String field1, CharSequence field2, CharSequence[] field3, Bundle field4, Parcelable field5, Parcelable[] field6, ParcelableClass field7, String[] field8, ParcelableClass[] field9) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
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
        intent.putExtra("field5", field5);
        intent.putExtra("field6", field6);
        intent.putExtra("field7", field7);
        intent.putExtra("field8", field8);
        intent.putExtra("field9", field9);
        return intent;
    }

    public static void inject(ClassesActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("ClassesActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        activity.field1 = bundle.getString("field1");
        activity.field2 = bundle.getCharSequence("field2");
        activity.field3 = bundle.getCharSequenceArray("field3");
        activity.field4 = bundle.getBundle("field4");
        activity.field5 = bundle.getParcelable("field5");
        activity.field6 = bundle.getParcelableArray("field6");
        activity.field7 = bundle.getParcelable("field7");
        activity.field8 = bundle.getStringArray("field8");
        Parcelable[] field9Value = bundle.getParcelableArray("field9");
        activity.field9 = new ParcelableClass[field9Value.length];
        System.arraycopy(field9Value, 0, activity.field9, 0, field9Value.length);
    }
}
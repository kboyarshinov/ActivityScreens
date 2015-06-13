package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.NullPointerException;
import java.lang.String;

import model.ParcelableClass;
import model.SerializableClass;

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
    public final Serializable field91;
    public final SerializableClass field92;

    public ClassesActivityScreen(String field1, CharSequence field2, CharSequence[] field3, Bundle field4, Parcelable field5, Parcelable[] field6, ParcelableClass field7, String[] field8, ParcelableClass[] field9, Serializable field91, SerializableClass field92) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
        this.field91 = field91;
        this.field92 = field92;
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
        Bundle bundle = new Bundle();
        bundle.putString("field1", field1);
        bundle.putCharSequence("field2", field2);
        bundle.putCharSequenceArray("field3", field3);
        bundle.putBundle("field4", field4);
        bundle.putParcelable("field5", field5);
        bundle.putParcelableArray("field6", field6);
        bundle.putParcelable("field7", field7);
        bundle.putStringArray("field8", field8);
        bundle.putParcelableArray("field9", field9);
        bundle.putSerializable("field91", field91);
        bundle.putSerializable("field92", field92);
        intent.putExtras(bundle);
        return intent;
    }

    public static void inject(ClassesActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("ClassesActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
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
        activity.field91 = bundle.getSerializable("field91");
        activity.field92 = (SerializableClass) bundle.getSerializable("field92");
    }

    private static void checkArguments(Bundle bundle) {
        if (!bundle.containsKey("field1")) {
            throw new IllegalStateException("Required argument field1 with key 'field1' is not set");
        }
        if (!bundle.containsKey("field2")) {
            throw new IllegalStateException("Required argument field2 with key 'field2' is not set");
        }
        if (!bundle.containsKey("field3")) {
            throw new IllegalStateException("Required argument field3 with key 'field3' is not set");
        }
        if (!bundle.containsKey("field4")) {
            throw new IllegalStateException("Required argument field4 with key 'field4' is not set");
        }
        if (!bundle.containsKey("field5")) {
            throw new IllegalStateException("Required argument field5 with key 'field5' is not set");
        }
        if (!bundle.containsKey("field6")) {
            throw new IllegalStateException("Required argument field6 with key 'field6' is not set");
        }
        if (!bundle.containsKey("field7")) {
            throw new IllegalStateException("Required argument field7 with key 'field7' is not set");
        }
        if (!bundle.containsKey("field8")) {
            throw new IllegalStateException("Required argument field8 with key 'field8' is not set");
        }
        if (!bundle.containsKey("field9")) {
            throw new IllegalStateException("Required argument field9 with key 'field9' is not set");
        }
        if (!bundle.containsKey("field91")) {
            throw new IllegalStateException("Required argument field91 with key 'field91' is not set");
        }
        if (!bundle.containsKey("field92")) {
            throw new IllegalStateException("Required argument field92 with key 'field92' is not set");
        }
    }
}
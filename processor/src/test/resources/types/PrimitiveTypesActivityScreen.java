package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.IllegalStateException;
import java.lang.NullPointerException;

public final class PrimitiveTypesActivityScreen {
    public final int field1;
    public final float field2;
    public final double field3;
    public final byte field4;
    public final boolean field5;
    public final char field6;
    public final short field7;
    public final long field8;

    public PrimitiveTypesActivityScreen(int field1, float field2, double field3, byte field4, boolean field5, char field6, short field7, long field8) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
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
        Intent intent = new Intent(activity, PrimitiveTypesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("field1", field1);
        bundle.putFloat("field2", field2);
        bundle.putDouble("field3", field3);
        bundle.putByte("field4", field4);
        bundle.putBoolean("field5", field5);
        bundle.putChar("field6", field6);
        bundle.putShort("field7", field7);
        bundle.putLong("field8", field8);
        intent.putExtras(bundle);
        return intent;
    }

    public static void inject(PrimitiveTypesActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("PrimitiveTypesActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
        activity.field1 = bundle.getInt("field1");
        activity.field2 = bundle.getFloat("field2");
        activity.field3 = bundle.getDouble("field3");
        activity.field4 = bundle.getByte("field4");
        activity.field5 = bundle.getBoolean("field5");
        activity.field6 = bundle.getChar("field6");
        activity.field7 = bundle.getShort("field7");
        activity.field8 = bundle.getLong("field8");
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
    }
}
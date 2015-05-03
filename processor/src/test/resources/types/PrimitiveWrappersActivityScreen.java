package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.Boolean;
import java.lang.Byte;
import java.lang.Character;
import java.lang.Double;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.NullPointerException;
import java.lang.Short;

public final class PrimitiveWrappersActivityScreen {
    public final Character field1;
    public final Integer field2;
    public final Double field3;
    public final Float field4;
    public final Boolean field5;
    public final Short field6;
    public final Byte field7;
    public final Long field8;

    public PrimitiveWrappersActivityScreen(Character field1, Integer field2, Double field3, Float field4, Boolean field5,
                                 Short field6, Byte field7, Long field8) {
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
        Intent intent = new Intent(activity, PrimitiveWrappersActivity.class);
        intent.putExtra("field1", field1);
        intent.putExtra("field2", field2);
        intent.putExtra("field3", field3);
        intent.putExtra("field4", field4);
        intent.putExtra("field5", field5);
        intent.putExtra("field6", field6);
        intent.putExtra("field7", field7);
        intent.putExtra("field8", field8);
        return intent;
    }

    public static void inject(PrimitiveWrappersActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("PrimitiveWrappersActivity has empty Bundle. Use open() or openForResult() to launch activity.");
        }
        checkArguments(bundle);
        activity.field1 = bundle.getChar("field1");
        activity.field2 = bundle.getInt("field2");
        activity.field3 = bundle.getDouble("field3");
        activity.field4 = bundle.getFloat("field4");
        activity.field5 = bundle.getBoolean("field5");
        activity.field6 = bundle.getShort("field6");
        activity.field7 = bundle.getByte("field7");
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
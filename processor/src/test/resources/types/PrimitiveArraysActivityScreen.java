package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class PrimitiveArraysActivityScreen {
    public final int array1[];
    public final float array2[];
    public final double array3[];
    public final byte array4[];
    public final boolean array5[];
    public final char array6[];
    public final short array7[];
    public final long array8[];

    public PrimitiveArraysActivityScreen(int array1[], float array2[], double array3[], byte array4[], boolean array5[], char array6[], short array7[], long array8[]) {
        this.array1 = array1;
        this.array2 = array2;
        this.array3 = array3;
        this.array4 = array4;
        this.array5 = array5;
        this.array6 = array6;
        this.array7 = array7;
        this.array8 = array8;
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
        Intent intent = new Intent(activity, PrimitiveArraysActivity.class);
        intent.putExtra("array1", array1);
        intent.putExtra("array2", array2);
        intent.putExtra("array3", array3);
        intent.putExtra("array4", array4);
        intent.putExtra("array5", array5);
        intent.putExtra("array6", array6);
        intent.putExtra("array7", array7);
        intent.putExtra("array8", array8);
        return intent;
    }

    public static void inject(PrimitiveArraysActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        activity.array1 = bundle.getIntArray("array1");
        activity.array2 = bundle.getFloatArray("array2");
        activity.array3 = bundle.getDoubleArray("array3");
        activity.array4 = bundle.getByteArray("array4");
        activity.array5 = bundle.getBooleanArray("array5");
        activity.array6 = bundle.getCharArray("array6");
        activity.array7 = bundle.getShortArray("array7");
        activity.array8 = bundle.getLongArray("array8");
    }
}
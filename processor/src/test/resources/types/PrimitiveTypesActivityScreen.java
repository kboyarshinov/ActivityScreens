package types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class PrimitiveTypesActivityScreen {
    private PrimitiveTypesActivityScreen() {
    }

    public static void open(Activity activity, int fieldInt, float fieldFloat, double fieldDouble,
                            byte fieldByte, boolean fieldBoolean, char fieldChar, short fieldShort, long fieldLong) {
        Intent intent = createIntent(activity, fieldInt, fieldFloat, fieldDouble, fieldByte, fieldBoolean, fieldChar,
                fieldShort, fieldLong);
        activity.startActivity(intent);
    }

    public static void openForResult(Activity activity, int fieldInt, float fieldFloat, double fieldDouble,
                                     byte fieldByte, boolean fieldBoolean, char fieldChar, short fieldShort,
                                     long fieldLong int requestCode) {
        Intent intent = createIntent(activity, fieldInt, fieldFloat, fieldDouble, fieldByte, fieldBoolean, fieldChar,
                fieldShort, fieldLong);
        activity.startActivityForResult(intent, requestCode);
    }

    public static Intent createIntent(Activity activity, int fieldInt, float fieldFloat, double fieldDouble,
            byte fieldByte, boolean fieldBoolean, char fieldChar, short fieldShort, long fieldLong) {
        Intent intent = new Intent(activity, PrimitiveTypesActivity.class);
        intent.putExtra("fieldInt", fieldInt);
        intent.putExtra("fieldFloat", fieldFloat);
        intent.putExtra("fieldDouble", fieldDouble);
        intent.putExtra("fieldByte", fieldByte);
        intent.putExtra("fieldBoolean", fieldBoolean);
        intent.putExtra("fieldChar", fieldChar);
        intent.putExtra("fieldShort", fieldShort);
        intent.putExtra("fieldLong", fieldLong);
        return intent;
    }

    public static void inject(PrimitiveTypesActivity activity) {
        Bundle bundle = activity.getIntent().getExtras();
        activity.fieldBoolean = bundle.getBoolean("fieldBoolean");
        activity.fieldByte = bundle.getByte("fieldByte");
        activity.fieldChar = bundle.getChar("fieldChar");
        activity.fieldDouble = bundle.getDouble("fieldDouble");
        activity.fieldFloat = bundle.getFloat("fieldFloat");
        activity.fieldInt = bundle.getInt("fieldInt");
        activity.fieldLong = bundle.getLong("fieldLong");
        activity.fieldShort = bundle.getShort("fieldShort");
    }
}
package model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableClass implements Parcelable {
    private final int field;

    public ParcelableClass(int field) {
        this.field = field;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(field);
    }

    public static final Creator<ParcelableClass> CREATOR = new ClassLoaderCreator<ParcelableClass>() {
        @Override
        public ParcelableClass createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return createFromParcel(parcel);
        }

        @Override
        public ParcelableClass createFromParcel(Parcel parcel) {
            int field = parcel.readInt();
            return new ParcelableClass(field);
        }

        @Override
        public ParcelableClass[] newArray(int i) {
            return new ParcelableClass[0];
        }
    };
}
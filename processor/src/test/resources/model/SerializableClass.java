package model;

import java.io.Serializable;

public class SerializableClass implements Serializable {
    private final int field;

    public SerializableClass(int field) {
        this.field = field;
    }
}
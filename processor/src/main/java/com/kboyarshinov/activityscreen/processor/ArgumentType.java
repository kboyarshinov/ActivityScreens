package com.kboyarshinov.activityscreen.processor;

import java.util.HashMap;

/**
 * @author Kirill Boyarshinov
 */
public enum ArgumentType {
    INT("int", "Int"),
    INT_ARRAY("int[]", "IntArray"),
    LONG("long", "Long"),
    LONG_ARRAY("long[]", "LongArray"),
    DOUBLE("double", "Double"),
    DOUBLE_ARRAY("double[]", "DoubleArray"),
    SHORT("short", "Short"),
    SHORT_ARRAY("short[]", "ShortArray"),
    FLOAT("float", "Float"),
    FLOAT_ARRAY("float[]", "FloatArray"),
    BYTE("byte", "Byte"),
    BYTE_ARRAY("byte[]", "ByteArray"),
    BOOLEAN("boolean", "Boolean"),
    BOOLEAN_ARRAY("boolean[]", "BooleanArray"),
    CHAR("char", "Char"),
    CHAR_ARRAY("char[]", "CharArray"),
    CHARACTER("java.lang.Character", "Char"),
    INT_CLASS("java.lang.Integer", "Int"),
    LONG_CLASS("java.lang.Long", "Long"),
    DOUBLE_CLASS("java.lang.Double", "Double"),
    SHORT_CLASS("java.lang.Short", "Short"),
    FLOAT_CLASS("java.lang.Float", "Float"),
    BYTE_CLASS("java.lang.Byte", "Byte"),
    BOOLEAN_CLASS("java.lang.Boolean", "Boolean"),
    STRING("java.lang.String", "String"),
    CHARSEQUENCE("java.lang.CharSequence", "CharSequence"),
    CHARSEQUENCE_ARRAY("java.lang.CharSequence[]", "CharSequenceArray"),
    BUNDLE("android.os.Bundle", "Bundle"),
    PARCELABLE("android.os.Parcelable", "Parcelable"),
    PARCELABLE_ARRAY("android.os.Parcelable[]", "ParcelableArray"),
    PARCELABLE_WILDCARD("Class<? extends Parcelable>", "Parcelable");

    public final String rawType;
    public final String name;

    ArgumentType(String rawType, String name) {
        this.rawType = rawType;
        this.name = name;
    }

    public static ArgumentType from(String rawType) {
        return types.get(rawType);
    }

    private static HashMap<String, ArgumentType> types = new HashMap<String, ArgumentType>();
    static {
        for (ArgumentType type : ArgumentType.values()) {
            types.put(type.rawType, type);
        }
    }
}

package com.kboyarshinov.activityscreen.processor.args;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

/**
 * @author Kirill Boyarshinov
 */
public class ParcelableArrayArgument extends Argument {

    public ParcelableArrayArgument(String name, String key, String operation, TypeName typeName) {
        super(name, key, operation, typeName);
    }

    @Override
    public void generateGetMethod(MethodSpec.Builder builder) {
        String name = getName();
        builder.addStatement("$T[] $LValue = bundle.get$L($S)", ClassName.get("android.os", "Parcelable"), name, getOperation(), getKey());
        builder.addStatement("activity.$L = new $T[$LValue.length]", name, ((ArrayTypeName) getTypeName()).componentType, name);
        builder.addStatement("System.arraycopy($LValue, 0, activity.$L, 0, $LValue.length)", name, name, name);
    }
}

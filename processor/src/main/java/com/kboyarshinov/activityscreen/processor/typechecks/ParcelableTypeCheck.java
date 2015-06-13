package com.kboyarshinov.activityscreen.processor.typechecks;

import com.kboyarshinov.activityscreen.processor.Argument;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

/**
 * @author Kirill Boyarshinov
 */
public final class ParcelableTypeCheck implements TypeCheck {
    @Override
    public boolean check(TypeMirror typeMirror, TypeElements typeElements) {
        TypeMirror parcelable = typeElements.getElement("android.os.Parcelable").asType();
        return typeElements.isAssignable(typeMirror, parcelable);
    }

    @Override
    public Argument toArgument(String name, String key, TypeName typeName) {
        String parcelable = "Parcelable";
        return new Argument(name, key, parcelable, parcelable, typeName);
    }
}

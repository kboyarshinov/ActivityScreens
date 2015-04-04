package com.kboyarshinov.activityscreen.processor;

import com.kboyarshinov.activityscreens.annotation.ActivityArg;

import javax.lang.model.element.Element;

/**
 * Holds the information about a class annotated with @ActivityArg
 *
 * @author Kirill Boyarshinov
 */
public class ActivityArgAnnotatedField implements Comparable<ActivityArgAnnotatedField> {
    private final Element element;
    private final String name;
    private final String key;
    private final String type;
    private final boolean required;

    public ActivityArgAnnotatedField(Element element) {
        this.name = element.getSimpleName().toString();
        this.key = getKey(element);
        this.type = element.asType().toString();
        this.element = element;
        this.required = isRequired(element);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public String getVariableName() {
        return getVariableName(name);
    }

    public Element getElement() {
        return element;
    }

    @Override
    public String toString() {
        return key + "/" + type;
    }

    public String getRawType() {
        if (isArray()) {
            return type.substring(0, type.length() - 2);
        }
        return type;
    }

    public boolean isArray() {
        return type.endsWith("[]");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityArgAnnotatedField)) return false;

        ActivityArgAnnotatedField that = (ActivityArgAnnotatedField) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(ActivityArgAnnotatedField o) {
        return getVariableName().compareTo(o.getVariableName());
    }

    public static String getVariableName(String name) {
        if (name.matches("^m[A-Z]{1}")) {
            return name.substring(1, 2).toLowerCase();
        } else if (name.matches("m[A-Z]{1}.*")) {
            return name.substring(1, 2).toLowerCase() + name.substring(2);
        }
        return name;
    }

    private static String getKey(Element element) {
        ActivityArg annotation = element.getAnnotation(ActivityArg.class);
        String field = element.getSimpleName().toString();
        if (!"".equals(annotation.key())) {
            return annotation.key();
        }
        return getVariableName(field);
    }

    private static boolean isRequired(Element element) {
        ActivityArg annotation = element.getAnnotation(ActivityArg.class);
        return annotation.required();
    }
}

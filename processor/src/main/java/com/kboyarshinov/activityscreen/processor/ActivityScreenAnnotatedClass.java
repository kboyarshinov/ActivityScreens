package com.kboyarshinov.activityscreen.processor;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Holds the information about a class annotated with @ActivityScreen
 *
 * @author Kirill Boyarshinov
 */
public class ActivityScreenAnnotatedClass {
    public static final String SUFFIX = "Screen";
    private TypeElement annotatedClassElement;

    private Set<ActivityArgAnnotatedField> requiredFields = new TreeSet<ActivityArgAnnotatedField>();
    private Set<ActivityArgAnnotatedField> optionalFields = new TreeSet<ActivityArgAnnotatedField>();
    private Map<String, ActivityArgAnnotatedField> bundleKeyMap = new HashMap<String, ActivityArgAnnotatedField>();

    public ActivityScreenAnnotatedClass(TypeElement classElement) {
        this.annotatedClassElement = classElement;
    }

    /**
     * Checks if a field (with the given name) is already in this class
     */
    public boolean containsField(ActivityArgAnnotatedField field) {
        return requiredFields.contains(field) || optionalFields.contains(field);
    }

    /**
     * Checks if a key for a bundle has already been used
     */
    public boolean containsBundleKey(ActivityArgAnnotatedField field) {
        return bundleKeyMap.get(field.getKey()) != null;
    }

    public void addFieldClass(ActivityArgAnnotatedField field) {
        bundleKeyMap.put(field.getKey(), field);
        if (field.isRequired()) {
            requiredFields.add(field);
        } else {
            optionalFields.add(field);
        }
    }

    public Set<ActivityArgAnnotatedField> getRequiredFields() {
        return requiredFields;
    }

    public Set<ActivityArgAnnotatedField> getOptionalFields() {
        return optionalFields;
    }

    public ActivityArgAnnotatedField getFieldByKey(String key) {
        return bundleKeyMap.get(key);
    }

    /**
     * The original element that was annotated with @Screen
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}

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

    /**
     * The original element that was annotated with @Screen
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}

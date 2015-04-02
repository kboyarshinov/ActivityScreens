package com.kboyarshinov.activityscreen.processor;

import javax.lang.model.element.Element;

/**
 * Holds the information about a class annotated with @ActivityArg
 *
 * @author Kirill Boyarshinov
 */
public class ActivityArgAnnotatedField {
    private final Element element;

    public ActivityArgAnnotatedField(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}

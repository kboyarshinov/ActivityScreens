package com.kboyarshinov.activityscreen.processor;

import javax.lang.model.element.Element;

/**
 * @author Kirill Boyarshinov
 */
public class UnsupportedTypeException extends RuntimeException {

    private final Element element;

    public UnsupportedTypeException(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public String getMessage() {
        return "Unsupported type annotated with @ActivityArg: " + element.asType();
    }
}

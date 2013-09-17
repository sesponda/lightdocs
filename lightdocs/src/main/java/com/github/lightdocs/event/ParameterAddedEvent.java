package com.github.lightdocs.event;

import java.lang.annotation.*;
import java.util.Arrays;


import com.github.lightdocs.model.*;


/**
 * Listener of this event can introspect the annotations found in the java
 * method argument that caused the addition
 */
public class ParameterAddedEvent {

    private Annotation[] argumentAnnotations;
    private Parameter parameter;

    public ParameterAddedEvent(Parameter param, Annotation... argumentAnnotations) {
        this.parameter = param;
        this.argumentAnnotations = Arrays.copyOf(argumentAnnotations, argumentAnnotations.length);
    }

    public Annotation[] getParameterAnnotations() {
        return Arrays.copyOf(argumentAnnotations, argumentAnnotations.length);
    }

    public Parameter getParameter() {
        return parameter;
    }

}

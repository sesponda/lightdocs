package com.github.lightdocs.event;

import java.lang.reflect.*;


import com.github.lightdocs.model.*;


/**
 * Listener of this event can introspect the Method that caused the addition of
 * the Operation to the model, and augment the model processing custom
 * annotations, etc.
 */
public class OperationAddedEvent {
    private Method method;
    private Operation operation;

    public OperationAddedEvent(Method method, Operation operation) {
        super();
        this.method = method;
        this.operation = operation;
    }

    public Method getMethod() {
        return method;
    }

    public Operation getOperation() {
        return operation;
    }
}

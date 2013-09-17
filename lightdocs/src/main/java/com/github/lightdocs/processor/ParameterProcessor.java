package com.github.lightdocs.processor;

import java.lang.annotation.*;
import java.lang.reflect.*;

import javax.ws.rs.*;


import com.github.lightdocs.event.*;
import com.github.lightdocs.model.*;
import com.google.common.eventbus.*;

public class ParameterProcessor {

    private EventBus eventsBus;

    public ParameterProcessor(EventBus eventsBus) {
        this.eventsBus = eventsBus;
    }

    public ParameterProcessor() {
    }

    @Subscribe
    public void handle(OperationAddedEvent e) {
        Method method = e.getMethod();
        Operation operation = e.getOperation();
        for (Annotation[] argumentAnnotations : method.getParameterAnnotations()) {
            // LATER: MatrixParam and CookieParam
            Parameter param = null;
            for (Annotation ann : argumentAnnotations) {
                Class<?> annType = ann.annotationType();

                if (annType.equals(QueryParam.class)) {
                    param = operation.queryParameter(((QueryParam) ann).value());
                    break;
                } else if (annType.equals(HeaderParam.class)) {
                    param = operation.headerParameter(((HeaderParam) ann).value());
                    break;
                } else if (annType.equals(PathParam.class)) {
                    param = operation.pathParameter(((PathParam) ann).value());
                    break;
                }
            }
            if (param != null) {
                for (Annotation ann : argumentAnnotations) {
                    if (ann.annotationType().equals(DefaultValue.class)) {
                        String val = ((DefaultValue) ann).value();
                        param.setDefaultValue(val);
                        break;
                    }
                }
                if (eventsBus != null) {
                    eventsBus.post(new ParameterAddedEvent(param, argumentAnnotations));
                }
            }
        }
    }
}

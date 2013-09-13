package org.lightdocs.processor;

import java.lang.reflect.*;
import java.util.*;

import javax.ws.rs.*;

import org.lightdocs.event.*;
import org.lightdocs.model.*;

import com.google.common.eventbus.*;


public class ContentTypesProcessor {
    @Subscribe
    public void handle(OperationAddedEvent e) {
        Method method = e.getMethod();
        Operation operation = e.getOperation();
        Consumes c = method.getAnnotation(Consumes.class);
        if (c != null) {
            operation.setAcceptedContentTypes(Arrays.asList(c.value()));
        }
        Produces p = method.getAnnotation(Produces.class);
        if (p != null) {
            operation.setProducedContentTypes(Arrays.asList(p.value()));
        }
    }

}

package org.lightdocs.processor;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.lang3.Validate;
import org.lightdocs.dsl.DocumentedBy;
import org.lightdocs.event.OperationAddedEvent;
import org.lightdocs.event.ParameterAddedEvent;
import org.lightdocs.model.Operation;

import com.google.common.base.Charsets;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Resources;


public class DocumentedByAnnotationProcessor {

    private static final String DSL_CONTAINER_SCRIPT;

    static {
        URL url = DocumentedByAnnotationProcessor.class.getResource("DSLContainer.groovy");
        try {
            DSL_CONTAINER_SCRIPT = Resources.toString(url, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe
    public void handle(OperationAddedEvent e) { 
        Method method = e.getMethod();
        Operation operation = e.getOperation();
        DocumentedBy ann = method.getAnnotation(DocumentedBy.class);

        if (ann != null) {
            runDocumentationScriptInAnnotation(operation, ann);
        }
    }

    private void runDocumentationScriptInAnnotation(Object node, DocumentedBy annotation) {
        String script = loadScript(Validate.notBlank(annotation.value()));
        Binding binding = new Binding();
        binding.setVariable("node", node);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(DSL_CONTAINER_SCRIPT.replace("${docScript}", script));
    }

    private String loadScript(String resPath) {
        String script;
        try {
            URL resUrl;
            if (resPath.toLowerCase().startsWith("http://") || resPath.toLowerCase().startsWith("file://")) {
                resUrl = new URL(resPath);
            } else {
                resUrl = getClass().getResource(resPath);
            }
            script = Resources.toString(resUrl, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return script;
    }

    @Subscribe
    public void handle(ParameterAddedEvent e) {
        for (Annotation annotation : e.getParameterAnnotations()) {
            if (annotation.annotationType().equals(DocumentedBy.class)) {
                runDocumentationScriptInAnnotation(e.getParameter(), (DocumentedBy) annotation);
                return;
            }
        }
    }
}

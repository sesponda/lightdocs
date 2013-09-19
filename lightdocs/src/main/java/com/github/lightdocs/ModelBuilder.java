/* 
   Copyright 2013 Sebastian Esponda

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.github.lightdocs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.Path;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.github.lightdocs.event.OperationAddedEvent;
import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Operation;
import com.github.lightdocs.model.Resource;
import com.github.lightdocs.model.ServiceDocumentation;
import com.github.lightdocs.processor.ContentTypesProcessor;
import com.github.lightdocs.processor.DocumentedByAnnotationProcessor;
import com.github.lightdocs.processor.ParameterProcessor;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.eventbus.EventBus;


/**
 * 
 * This class discovers and builds the basic structural model processing JAXRS
 * annotations in a set of classes.
 * 
 * Its main responsibility is to discover JAXRS elements and to create the basic
 * structural elements (Body, Parameters, etc.). While JAXRS elements are
 * discovered, it will publish events via a lightweight event bus, so that event
 * listeners can react and augment the documentation model.
 * 
 */
public class ModelBuilder {

    @VisibleForTesting
    EventBus eventBus;

    /**
     * Default constructor, which setups the generator with the default
     * processors.
     */
    public ModelBuilder() {
        eventBus = new EventBus();
        eventBus.register(new ContentTypesProcessor());
        eventBus.register(new ParameterProcessor(eventBus));
        eventBus.register(new DocumentedByAnnotationProcessor());
    }

    /**
     * Allow customization via the injection of a pre-configured event bus. The
     * class will not register any default listener, it assumes that they were
     * registered in the bus previously.
     * 
     * @param e
     *            required not null
     */
    public ModelBuilder(EventBus e) {
        eventBus = e;
    }

    /**
     * Builds a documentation model from a list of JAXRS annotated classes.
     * 
     * @param jaxrsClasses
     *            required non empty list of classes or interfaces
     * @return a ServiceDocumentation object
     */
    public ServiceDocumentation buildModelFor(String... jaxrsClasses) {
        Validate.notEmpty(jaxrsClasses);
        ServiceDocumentation model = new ServiceDocumentation();
        for (String className : jaxrsClasses) {
            Class<?> clazz = loadClass(className);
            Validate.isTrue(hasPathAnnotation(clazz), "No JAXRS annotations were present on " + clazz);
            for (Method method : clazz.getMethods()) {
                if (hasHttpMethodAnnotation(method)) {
                    Resource resource = model.resource(getRootResourceURIFor(method.getDeclaringClass()));
                    HttpMethod operationMethod = HttpMethod.valueOf(findJAXRSHttpMethodAnnotation(method).value());
                    
                    String classPathValue = standardizePathAnnotationValue(method.getDeclaringClass().getAnnotation(Path.class));
                    String methodPathValue = standardizePathAnnotationValue(method.getAnnotation(Path.class));
                    
                    
                    String operationPath = buildEffectiveURIFor(classPathValue, methodPathValue);                    
                    Operation operation = resource.operation(operationMethod, operationPath);
                    eventBus.post(new OperationAddedEvent(method, operation));
                }
            }
        }
        return model;
    }

    @VisibleForTesting
    String buildEffectiveURIFor(String sbClass, String sbMethod) {        
        StringBuilder sb = new StringBuilder("/");        
        if (StringUtils.isNotBlank(sbClass)) {
            sb.append(sbClass).append("/");
        }
        if (StringUtils.isNotBlank(sbMethod)) {
            sb.append(sbMethod);
        }
        
        if (sb.lastIndexOf("/") == sb.length()-1) {
            sb.deleteCharAt(sb.length() -1);
        }
        return sb.toString();
    }

    @VisibleForTesting
    String getRootResourceURIFor(Class<?> c) {
        return '/'+standardizePathAnnotationValue(c.getAnnotation(Path.class));
    }

    /**
     * @param p
     * @return the value of the path annotation, removing slash characters at
     *         the beginning and end, if any. If the annotation is empty or null, returns "".
     */
    @VisibleForTesting
    String standardizePathAnnotationValue(Path p) {

        if (p == null || p.value() == null || StringUtils.isBlank(p.value())) {
            return "";
        }

        StringBuilder sb = new StringBuilder(p.value().trim());

        if ((sb.length() > 1) && (sb.charAt(0) == '/')) {
            sb.deleteCharAt(0);
        }
        if ((sb.length() > 1) && (sb.charAt(sb.length() - 1) == '/')) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private Class<?> loadClass(String classname) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(classname);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Traverses each method annotations checking if it is annotated with the
     * JAXRS HttpMethod annotation.
     * 
     * @param method
     *            required.
     * @return null if not found
     */
    private javax.ws.rs.HttpMethod findJAXRSHttpMethodAnnotation(Method method) {
        // all GET/POST/etc JAXRS annotations have the HttpMethod JAXRS
        // annotation on them
        for (Annotation a : method.getAnnotations()) {
            Class<? extends Annotation> aType = a.annotationType();
            if (aType.isAnnotationPresent(javax.ws.rs.HttpMethod.class)) {
                return aType.getAnnotation(javax.ws.rs.HttpMethod.class);
            }
        }
        return null;
    }

    /**
     * @param m
     *            required
     * @return true if any annotation in the method is annotated with the JAXRS
     *         HttpMethod annotation.
     */
    private boolean hasHttpMethodAnnotation(Method m) {
        return (findJAXRSHttpMethodAnnotation(m) != null);
    }

    /**
     * @param cls
     *            required
     * @return true if there is any Path or GET/POST/etc annotation.
     */
    private boolean hasPathAnnotation(Class<?> cls) {
        if (cls.isAnnotationPresent(Path.class)) {
            return true;
        } else {
            for (Method m : cls.getMethods()) {
                if (m.isAnnotationPresent(Path.class)) {
                    return true;
                }
            }
            return false;
        }
    }
}

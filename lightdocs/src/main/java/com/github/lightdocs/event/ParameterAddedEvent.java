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

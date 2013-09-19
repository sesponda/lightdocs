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

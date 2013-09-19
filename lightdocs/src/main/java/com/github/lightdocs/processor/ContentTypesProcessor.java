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
package com.github.lightdocs.processor;

import java.lang.reflect.*;
import java.util.*;

import javax.ws.rs.*;


import com.github.lightdocs.event.*;
import com.github.lightdocs.model.*;
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

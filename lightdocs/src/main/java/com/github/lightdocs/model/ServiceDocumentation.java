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
package com.github.lightdocs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.Validate;

/**
 * This is the aggregate root of the documentation model. It contains a set of
 * resources, each of them uniquely identified by its resource path.
 * 
 */
public class ServiceDocumentation {

    private final ConcurrentMap<String, Resource> resources;

    public ServiceDocumentation() {
        resources = new ConcurrentHashMap<String, Resource>();
    }

    public List<Resource> getResources() {
        return new ArrayList<Resource>(resources.values());
    }

    /**
     * Creates or returns a Resource
     * 
     * @param resourcePath
     *            required not null. Resource paths should start with / and not
     *            end with /
     * @return a located Resource, or a new one just created and linked to the
     *         specified path
     */
    public Resource resource(String resourcePath) {
        Validate.notBlank(resourcePath);
        Resource res = new Resource(resourcePath);
        Resource prev = resources.putIfAbsent(resourcePath, res);
        return (prev != null) ? prev : res;
    }
}

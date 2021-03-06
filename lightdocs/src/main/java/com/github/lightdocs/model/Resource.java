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

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

public class Resource {

    private String path;
    private String description;
    private ConcurrentMap<OperationPK, Operation> operations;

    public Resource(String path) {
        this.path = path;
        operations = new ConcurrentHashMap<OperationPK, Operation>(8, 0.9f, 1);
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Set<Operation> getOperations() {
        return ImmutableSet.copyOf(operations.values());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets or Creates an operation inside this resource. 
     * @param method
     *            required
     * @param path
     *            required. It should start with slash.
     * @return an operation matching the criteria (if exists). If it does not exists, it will be created and added 
     * to the internal resource collection.
     * @throws NoSuchElementException
     *             if not found
     */
    public Operation operation(HttpMethod method, final String path) {
        
        OperationPK opk = new OperationPK(method, path);
        Operation result = operations.get(opk);
        if (result == null) {
            Operation newOp = new Operation(method, path);
            result = operations.putIfAbsent(opk, newOp);
            if (result == null) {
                result = newOp;
            }
        }
        return result;
    }
    
    
}

class OperationPK {
    private final HttpMethod method;
    private final String path;
    OperationPK(HttpMethod _method, final String _path) {
        method = _method;
        path = _path;
    }
    @Override
    public int hashCode()
    {
        return Objects.hashCode(method, path);
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof OperationPK){
            final OperationPK other = (OperationPK) obj;
            return Objects.equal(method, other.method) && (Objects.equal(path, other.path));
        } else{
            return false;
        }
    }
}
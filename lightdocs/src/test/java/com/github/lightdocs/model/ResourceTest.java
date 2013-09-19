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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Resource;


public class ResourceTest
{
    @Test
    public void operationShouldBeAddedOnlyIfItDoesNotExists() {
        Resource r = new Resource("/path");
        assertThat(r.getOperations().size(), is(0));
        
        assertThat(r.operation(HttpMethod.GET, "/user"),notNullValue());
        assertThat(r.getOperations().size(), is(1));
        
        assertThat(r.operation(HttpMethod.GET, "/user"),notNullValue());
        assertThat(r.getOperations().size(), is(1));
        
        assertThat(r.operation(HttpMethod.GET, "/car"),notNullValue());
        assertThat(r.getOperations().size(), is(2));
        
        assertThat(r.operation(HttpMethod.DELETE, "/car"),notNullValue());
        assertThat(r.getOperations().size(), is(3));
        
        assertThat(r.operation(HttpMethod.DELETE, "/lane"),notNullValue());
        assertThat(r.getOperations().size(), is(4));
    }

}

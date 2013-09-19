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

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Operation;


public class OperationTest {

    @Test
    public void parameterShouldBeAddedOnlyIfItDoesNotExists() {
        Operation op = new Operation(HttpMethod.DELETE, "/plane");
        
        assertThat(op.getHeaderParameters(), hasSize(0));        
        assertThat(op.headerParameter("h1"),notNullValue());        
        assertThat(op.getHeaderParameters(), hasSize(1));
        assertThat(op.headerParameter("h1"),notNullValue());
        assertThat(op.getHeaderParameters(), hasSize(1));
        assertThat(op.headerParameter("h2"),notNullValue());
        assertThat(op.getHeaderParameters(), hasSize(2));
        
        assertThat(op.getPathParameters(), hasSize(0));        
        assertThat(op.pathParameter("p1"),notNullValue());        
        assertThat(op.getPathParameters(), hasSize(1));
        assertThat(op.pathParameter("p1"),notNullValue());
        assertThat(op.getPathParameters(), hasSize(1));
        assertThat(op.pathParameter("p2"),notNullValue());
        assertThat(op.getPathParameters(), hasSize(2));
        
        assertThat(op.getQueryParameters(), hasSize(0));        
        assertThat(op.queryParameter("p1"),notNullValue());        
        assertThat(op.getQueryParameters(), hasSize(1));
        assertThat(op.queryParameter("p1"),notNullValue());
        assertThat(op.getQueryParameters(), hasSize(1));
        assertThat(op.queryParameter("p2"),notNullValue());
        assertThat(op.getQueryParameters(), hasSize(2));
    }

}

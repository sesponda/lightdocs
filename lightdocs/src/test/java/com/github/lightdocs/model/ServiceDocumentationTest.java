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

import com.github.lightdocs.model.ServiceDocumentation;


public class ServiceDocumentationTest {

    @Test
    public void resourceMethodShouldNotCreateExistentResources() {
        ServiceDocumentation sd = new ServiceDocumentation();
        assertThat(sd.getResources().size(), equalTo(0));

        // this should trigger a creation
        sd.resource("someResource");
        assertThat(sd.getResources().size(), equalTo(1));

        // the second get should not have created a new resource.
        sd.resource("someResource");
        assertThat(sd.getResources().size(), equalTo(1));

        // this should trigger a creation
        sd.resource("someResource2");
        assertThat(sd.getResources().size(), equalTo(2));

        // this should not have created a new resource.
        sd.resource("someResource");
        assertThat(sd.getResources().size(), equalTo(2));

        sd.resource("someResource/other");
        assertThat(sd.getResources().size(), equalTo(3));

        sd.resource("someResource/other");
        assertThat(sd.getResources().size(), equalTo(3));
    }
}

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

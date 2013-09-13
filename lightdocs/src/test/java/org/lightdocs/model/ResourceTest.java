package org.lightdocs.model;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;


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

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

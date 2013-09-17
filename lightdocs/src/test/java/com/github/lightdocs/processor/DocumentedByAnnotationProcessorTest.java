package com.github.lightdocs.processor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.github.lightdocs.ModelBuilder;
import com.github.lightdocs.dsl.DocumentedBy;
import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Operation;
import com.github.lightdocs.model.Parameter;
import com.github.lightdocs.model.Resource;
import com.github.lightdocs.model.ServiceDocumentation;

public class DocumentedByAnnotationProcessorTest {

    @Test
    public void testAnnotationAtMethodLevel() throws IOException {

        ServiceDocumentation doc = new ModelBuilder().buildModelFor(DocumentedByOnMethodResource.class.getName());
        Resource r = doc.getResources().get(0);
        assertThat(r.getPath(), equalTo("/"));
        Operation o = r.operation(HttpMethod.GET, "/{id1}");

        assertThat(o.getDescription(), equalTo("Operation description, example 1"));
        assertThat(o.getRequestBody().getExample(), equalTo("RequestBody Example."));
        assertThat(o.getRequestBody().getDescription(), startsWith("\n  This is a sample description,"));
        assertThat(o.getHeaderParameters().size(), equalTo(1));

        // validate header parameters
        Parameter p = o.getHeaderParameters().iterator().next();
        assertThat(p.getName(), equalTo("X-Auth"));
        assertThat(p.getDescription(), equalTo("This is a sample documentation for header parameter X-Auth"));
        assertThat(p.getDefaultValue(), equalTo("def-auth"));
        assertThat(p.isRequired(), equalTo(true));
        assertThat(p.getValidationRules(), equalTo("validation rules A"));

        // validate query parameters
        p = o.getQueryParameters().iterator().next();
        assertThat(p.getValidationRules(), equalTo("must be regexp x"));
        assertThat(o.getPathParameters().size(), equalTo(1));

        // validate path parameters
        p = o.getPathParameters().iterator().next();
        assertThat(p.getDescription(), equalTo("This is a sample documentation for a path parameter."));

    }

    // @Test
    public void testAnnotationAtTypeLevel() throws IOException {
        // TODO this requires a new type of event to be dispatched
    }

    @Test
    public void testAnnotationAtParameterLevel() throws IOException {
        ServiceDocumentation doc = new ModelBuilder().buildModelFor(DocumentedByOnParameterResource.class.getName());
        Resource r = doc.getResources().get(0);
        assertThat(r.getPath(), equalTo("/"));
        Operation o = r.operation(HttpMethod.GET, "/{id1}");

        assertThat(o.getPathParameters().size(), equalTo(1));
        Parameter p = o.getPathParameters().iterator().next();
        assertThat(p.getName(), equalTo("id1"));
        assertThat(p.getDescription(), equalTo("id1 parameter description"));
        assertThat(p.getDefaultValue(), equalTo("default id is 42"));
        assertThat(p.isRequired(), equalTo(false));
        assertThat(p.getValidationRules(), equalTo("should be an int"));
    }

}

abstract class DocumentedByOnMethodResource {

    @GET
    @Path("/{id1}")
    @DocumentedBy("/documentation-method.groovy")
    public abstract Response get(@HeaderParam("X-Auth") String xa, @PathParam("id1") String id,
            @QueryParam("q1") String q1);
}

abstract class DocumentedByOnParameterResource {

    @GET
    @Path("/{id1}")
    public abstract Response get(@HeaderParam("X-Auth") String xa,
            @PathParam("id1") @DocumentedBy("/documentation-param.groovy") String id,
            @QueryParam("q1") String q1);
}

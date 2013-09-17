package com.github.lightdocs.processor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.github.lightdocs.ModelBuilder;
import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Operation;
import com.github.lightdocs.model.ServiceDocumentation;


public class ContentTypesProcessorTest {

    @Test
    public void testParametersAreAdded() {
        ServiceDocumentation doc = new ModelBuilder().buildModelFor(ContentTypesProcessorTestResource.class.getName());

        Operation op1 = doc.resource("/users").operation(HttpMethod.DELETE, "/users/{id3}");

        assertThat(op1.getAcceptedContentTypes(),
                containsInAnyOrder(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));
        assertThat(op1.getProducedContentTypes(),
                containsInAnyOrder(MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_OCTET_STREAM));
    }

}

@Path("/users")
abstract class ContentTypesProcessorTestResource {

    @GET
    @Path("/{id1}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response get(@HeaderParam("X-Auth") String xa, @PathParam("id1") String id,
            @QueryParam("q1") String q1, @QueryParam("q2") String q2);

    @PUT
    @Path("{id2}/")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response update(@PathParam("id2") String id, String body);

    @POST
    public abstract Response create(String body);

    @DELETE
    @Path("{id3}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_OCTET_STREAM })
    public abstract Response delete(@PathParam("id3") String id, String body);
}

package org.lightdocs.test.resources;

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

/**
 * Class used only for testing purposes.
 * 
 */
@Path("/users")
public interface UsersResource {
    
    @POST
    Response create(String body);
    
    @DELETE
    @Path("{id1}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response delete(@PathParam("id3") String id, String body);

    @GET
    @Path("{id1}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@HeaderParam("X-Auth") String xa, @PathParam("id1") String id, @QueryParam("q1") String q1,
            @QueryParam("q2") String q2);

    void nonJAXRSMethod(); // this should not appear in the documentation
    
    @PUT
    @Path("{id1}")
    @Produces(MediaType.APPLICATION_JSON)
    Response update(@PathParam("id2") String id, String body);
}

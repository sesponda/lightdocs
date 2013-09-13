package org.lightdocs.test.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Class used only for testing purposes.
 * 
 */
@Path("/users/")
public interface MoreUsersResource {

    @GET
    @Path("archived/{id1}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@HeaderParam("X-Auth") String xa, @PathParam("id1") String id, @QueryParam("q1") String q1,
            @QueryParam("q2") String q2);

    @PUT
    @Path("archived/{id2}/")
    @Produces(MediaType.APPLICATION_JSON)
    Response update(@PathParam("id2") String id, String body);

    @POST
    Response create(String body);
}

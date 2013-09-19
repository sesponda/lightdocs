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
package com.github.lightdocs.test.resources;

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

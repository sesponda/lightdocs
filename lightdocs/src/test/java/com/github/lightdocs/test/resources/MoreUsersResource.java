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

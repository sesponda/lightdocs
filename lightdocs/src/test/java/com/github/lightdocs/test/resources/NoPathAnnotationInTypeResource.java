package com.github.lightdocs.test.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Class used only for testing purposes.
 * 
 */
public interface NoPathAnnotationInTypeResource {
    
    @POST
    @Path("/")
    Response create(String body);
}

package org.lightdocs.examples.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.lightdocs.ModelBuilder;
import org.lightdocs.model.ServiceDocumentation;


@Resource
@Path("/api")
public class API {

    private ServiceDocumentation apidocs;
    public API(List<String> classes) {
        this.apidocs = new ModelBuilder().buildModelFor(classes.toArray(new String[classes.size()]));        
    }

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON})
    public Response getAPIDocModel() {
        return Response.ok(apidocs).build();
    }
}

package org.lightdocs.examples.resource;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.lightdocs.dsl.DocumentedBy;
import org.lightdocs.examples.model.DummyResponse;
import org.springframework.stereotype.Service;

@Service
@Resource
@Path("/catalog")
public class Catalog {
    @GET
    @Path("/titles")
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @DocumentedBy("/docs/catalog-list.groovy")
    public Response listTitles(@QueryParam("search_term") String search_term,
            @QueryParam("start_idx") @DefaultValue("0") int startIdx,
            @QueryParam("max_results") @DefaultValue("100") int maxResults,
            @HeaderParam("auth_token") @DocumentedBy("/docs/auth-token.groovy") String auth) {

        return Response.ok(new DummyResponse(search_term, startIdx, maxResults, auth)).build();
    }

    @GET
    @Path("/titles/${id}")
    @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @DocumentedBy("/docs/catalog-get.groovy")
    public Response getTitle(@PathParam("id") String id, @QueryParam("field") String[] fields,
            @DocumentedBy("/docs/auth-token.groovy") @HeaderParam("auth_token") String auth) {
        return Response.ok(new DummyResponse(id, Arrays.asList(fields))).build();
    }
    /*
     * @DELETE
     * 
     * @Path("/titles/${id}")
     * 
     * @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
     * }) public Response deleteTitle(@PathParam("id") String id,
     * 
     * @HeaderParam("auth_token") String auth) { return Response.ok(new
     * DummyResponse(id)).build(); }
     * 
     * @PUT
     * 
     * @Path("/titles/${id}")
     * 
     * @Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
     * })
     * 
     * @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
     * }) public Response updateTitle(Title title, @HeaderParam("auth_token")
     * String auth) { return Response.ok(new DummyResponse(title)).build(); }
     * 
     * @POST
     * 
     * @Path("/titles")
     * 
     * @Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
     * })
     * 
     * @Produces(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
     * }) public Response createTitle(Title title, @HeaderParam("auth_token")
     * String auth) { return Response.ok(new DummyResponse(title)).build(); }
     */
}

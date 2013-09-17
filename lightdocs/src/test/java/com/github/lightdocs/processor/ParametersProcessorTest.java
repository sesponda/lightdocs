package com.github.lightdocs.processor;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import org.junit.Before;
import org.junit.Test;

import com.github.lightdocs.ModelBuilder;
import com.github.lightdocs.model.HttpMethod;
import com.github.lightdocs.model.Operation;
import com.github.lightdocs.model.Parameter;
import com.github.lightdocs.model.ServiceDocumentation;
import com.github.lightdocs.processor.ParameterProcessor;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.eventbus.EventBus;


public class ParametersProcessorTest {

    private ServiceDocumentation doc;

    @Before
    public void setUp() {
    	EventBus bus = new EventBus();
    	bus.register(new ParameterProcessor(bus));
        doc = new ModelBuilder(bus).buildModelFor(ParametersProcessorTestResource.class.getName());
    }

    @Test
    public void testHeaderParamIsAdded() {

        Operation op = doc.resource("/users").operation(HttpMethod.GET, "/users/{id1}");
        assertThat(op.getHeaderParameters(), hasSize(1));
        Parameter p = op.getHeaderParameters().iterator().next(); 
        assertThat(p.getName(), equalTo("X-Auth"));
        assertThat(p.getDefaultValue(), equalTo("a"));

    }

    @Test
    public void testQueryParamsAreAdded() {
        Operation op = doc.resource("/users").operation(HttpMethod.GET, "/users/{id1}");
        assertThat(op.getQueryParameters(), hasSize(2));
        List<String> paramNames = FluentIterable.from(op.getQueryParameters())
                .transform(new Function<Parameter, String>() {
                    public String apply(Parameter p) {
                        return p.getName();
                    }
                }).toList();
        assertThat(paramNames, containsInAnyOrder("q1", "q2"));
    }

    @Test
    public void testPathParamIsAdded() {
        Operation op = doc.resource("/users").operation(HttpMethod.DELETE, "/users/{id3}");
        assertThat(op.getPathParameters(), hasSize(1));
        Parameter p = op.getPathParameters().iterator().next(); 
        assertThat(p.getName(), equalTo("id3"));
        assertThat(p.getDefaultValue(), equalTo(null));
    }
}

@Path("/users")
abstract class ParametersProcessorTestResource {

    @GET
    @Path("/{id1}")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract Response get(@HeaderParam("X-Auth") @DefaultValue("a") String xa, @PathParam("id1") String id,
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

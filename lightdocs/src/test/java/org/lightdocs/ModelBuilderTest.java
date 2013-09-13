package org.lightdocs;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.Path;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.lightdocs.model.HttpMethod;
import org.lightdocs.model.Resource;
import org.lightdocs.model.ServiceDocumentation;
import org.lightdocs.test.resources.ClientsResource;
import org.lightdocs.test.resources.MoreUsersResource;
import org.lightdocs.test.resources.NoPathAnnotationInTypeResource;
import org.lightdocs.test.resources.UsersResource;

import com.google.common.eventbus.EventBus;

public class ModelBuilderTest {

    private ModelBuilder builder;

    @Before
    public void setUp() {
        this.builder = new ModelBuilder(new EventBus());
    } 

    @Test
    public void testBuildRootResourceURIFor() {
        assertThat(builder.getRootResourceURIFor(UsersResource.class), equalTo("/users")); 
        assertThat(builder.getRootResourceURIFor(NoPathAnnotationInTypeResource.class), equalTo("/"));
    }
    
    @Test
    public void testBuildEffectiveURi() {
        assertThat(builder.buildEffectiveURIFor("","users"), equalTo("/users")); 
        assertThat(builder.buildEffectiveURIFor("","users"), equalTo("/users"));
        assertThat(builder.buildEffectiveURIFor("users",""), equalTo("/users"));
        assertThat(builder.buildEffectiveURIFor("users","archived"), equalTo("/users/archived"));
    }

    @Test 
    public void testStandardizePathAnnotationValue() {
        assertThat(builder.standardizePathAnnotationValue(null), equalTo(""));
        Path path = mock(Path.class);
        assertThat(builder.standardizePathAnnotationValue(path), equalTo(""));
        when(path.value()).thenReturn("{id}");
        assertThat(builder.standardizePathAnnotationValue(path), equalTo("{id}"));
        when(path.value()).thenReturn("{id2}/");
        assertThat(builder.standardizePathAnnotationValue(path), equalTo("{id2}"));
        when(path.value()).thenReturn("/{id3}/");
        assertThat(builder.standardizePathAnnotationValue(path), equalTo("{id3}"));
        when(path.value()).thenReturn("/{id4}");
        assertThat(builder.standardizePathAnnotationValue(path), equalTo("{id4}"));
    }

    @SuppressWarnings("rawtypes")
    private static Matcher isOperation(HttpMethod m, String path) {
        return allOf(hasProperty("path", equalTo(path)), hasProperty("httpMethod", equalTo(m)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOperationsAreGroupedByResource() {
        ServiceDocumentation doc = builder.buildModelFor(MoreUsersResource.class.getName(), ClientsResource.class.getName(),
                UsersResource.class.getName());
        // We processed three classes, however there should be two resources
        // (not
        // three) because two classes have the same
        // resource path. As a result, their operations are grouped in the same
        // resource.
        assertThat(doc.getResources(), hasSize(2));
        Resource r = doc.resource("/users");
        assertThat(r.getOperations(), hasSize(6));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.POST, "/users")));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.PUT, "/users/{id1}")));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.GET, "/users/{id1}")));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.GET, "/users/archived/{id1}")));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.PUT, "/users/archived/{id2}")));
        assertThat(r.getOperations(), hasItem(isOperation(HttpMethod.DELETE, "/users/{id1}")));
    }

    @Test
    public void shouldAddResourcesWithNoPathAnnotation() {
        ServiceDocumentation doc = builder.buildModelFor(NoPathAnnotationInTypeResource.class.getName());
        assertThat(doc.getResources().size(), equalTo(1));
        Resource r = doc.resource("/");
        assertThat(r.getOperations().size(), equalTo(1));
    }

    @Test
    public void defaultConstructorShouldInitializeEventBus() {
        ModelBuilder b = new ModelBuilder();
        assertThat(b.eventBus != null, is(true));
    }
}

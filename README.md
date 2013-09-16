Home
====
**Lightdocs** is a framework for documenting web services implemented using the Java API for RESTful Services specification. Half of the work is done by the framework automatically extracting information from the JAX-RS annotations. The other half is augmenting the generated documentation using a DSL (Domain Specific Language) as needed.


Introduction
---
While it is true that REST services should be self-descriptive and developer-friendly, sometimes you need to document complex behaviors and concepts.  

The framework will automatically generate basic documentation such as:

* Enumerating resources and operations.
* Documenting supported content types.
* Listing required and optional Header/Path/Query parameters.

The generated model can be augmented using a Groovy based DSL, for example:

* Providing request/response examples.
* Explaining pre- and post-conditions.
* ...or any other concept you need to clarify to your API users.
 
You can have several independent DSL scripts and apply them to specific methods or parameters using an annotation. For example, if you have several methods that reuse the same complex data type, you can  create one DSL to describe that data type once, and re-apply it as needed. The DSL scripts can be packaged with the application, or located using file:// or http:// URIs.

The final documentation is exported in an object model that can be consumed by other clients.

_**Note:** there is a complementary project (yet to be released) that will be able to consume this data model and generate a user-friendly Web UI to publish such information, also including dynamic web-forms that will allow the developers to make quick test calls._


Description
---

The project has three main components:

1. **Documentation Model**: defined by simple JavaBeans such as `Resource`, `Operation`, `Parameter`, and others (see package _org.lightdocs.model_ ).
2. **Documentation Builder**: which will create the basic documentation structure processing JAX-RS annotations and firing events as new operations and resources are added. These events will be picked up by different processors that will augment the model. _Custom processors could be added to extends this project processing more annotations_.
3. **Documentation DSL**: a Groovy-based Domain Specific Language used to write about concepts and behaviors that can't be explained with the annotations alone.


How to use
---

###Step 1: import the required libraries

Note: the project packages are being uploaded following Sonar procedures, so they might not available yet (Sep 13th, 2014).

####Maven:
```xml
<dependency>
    <groupId>org.lightdocs</groupId>
    <artifactId>lightdocs</artifactId>
    <version>0.3</version>
</dependency>
            
```
####Gradle:
```groovy
dependencies {
    runtime group: 'org.lightdocs', name: 'lightdocs', version: '0.3'
}
```

###Step 2: create a `ModelBuilder` object listing your JAX-RS classes. 

The following example creates a web service that will export the generated documentation model in JSON.

```java
@Resource
@Path("/api")
public class API {
    private ServiceDocumentation apidocs;
    public API(String... classes) {
        this.apidocs = new ModelBuilder().buildModelFor(classes);        
    }
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON})
    public Response getAPIDocModel() {
        return Response.ok(apidocs).build();
    }
}
```

###Step 3 (optional): add more documentation using the provided DSL
For example:

```java
@GET
@Path("/user/${id}/tickets")
@DocumentedBy("getTickets.groovy")
void getTickets(@PathParam Integer id, @QueryParam("category") categoryFilter);
```

And then include the `getTickets.groovy` file with your resources:

```groovy
description = "Returns the open tickets for the user."

requestBody.description = """
  This is a sample description, written using a Groovy multi line string 
  (delimited with triple line quotes). Multi line strings can contain code and variables, 
  and do not need to escape <html/> or \n &nsbs;
  You can also include dynamic code that will be evaluated when the 
  script is run. For example: document generated at ${ new Date()}
  """
requestBody.example = "Here you can put a JSON example of the request body."
responseBody.example = "Here you can put a JSON example of the response body."

headerParam("X-Auth") {
  description = """(Note that the Java code does not include this 
  header parameter. However, we can still document it, for example 
  if it is something not yet fully implemented but required as per 
  the API spec.
  """
  required = true
  validationRules = "RegExp: [A-Z][A-Z0-9]*"
}

queryParam("category") {
  description = """One specific category to use as a filter. 
  If not specified, all non-archived categories will be returned.
  """
}

pathParam("id") {
  description = "The numeric ID of the user."
  required = true
}
```

Examples
---

The directory `lightdoc-examples` contains a ready-to-go example project. More examples might be added later.

| Subdir | Technologies | Instructions |
|---|---|---|
|resteasy-spring| Java, JBoss Resteasy, Spring IoC | run `mvn jetty:run` and browse http://localhost:8080/api to see the generated JSON model. Check Catalog.java and the DSL scripts (*.groovy files).
|cxf-guice| Java, Apache CXF, Guice| (not done yet...) |


Current development status - How to contribute
---

The current development status is "stable", however this is not yet v1.0 because it doesn't support all the possible JAX-RS use cases. For example, some annotations are ignored (e.g. Matrix parameters). I've prioritized the set of features that were needed for a personal project and then released the code "as is". If you find that something you need is still not implemented, feel free to fork the repository and code what is missing or contribute it back to project.

If you want to contribute, the standard [fork](https://help.github.com/articles/fork-a-repo) and [pull request](https://help.github.com/articles/using-pull-requests) strategy should work fine. However, please take into account that it would be best if we keep the project focused and small, adding more features in a modular way. Some examples:

- New annotations can be supported adding more processors (instead of adding code to the existent ones). 
- New processors could be added in a separate module. Example, `lightdocs-resteasy` to process Resteasy custom annotations such as `@ClientResponseType`.
- A Spring BeanPostProcessor could be added to `lightdocs-spring` to discover the list of JAXRS classes (instead of providing them as a list of classes).
- Etc...

#### Future development ideas
- _Convention over configuration_: simplify the set up of DSL scripts defining a strategy for their location (e.g. className-methodName.groovy). In this way, you don't need to specify the DSL location for each class method using the `@DocumentedBy` annotation (the framework will pick it up following the convention).
- _Automatic documentation of types_: everytime a complex datatype is used as the request or response object, introspect that Java bean and document the type. Beside introspecting the bean to list its attributes, JSR-303 or Jackson annotations could be processed to add more information. A type-oriented DSL could be added too.




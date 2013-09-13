package org.lightdocs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.ImmutableSet;

/**
 * A REST oriented operation (an HTTP request) which can be executed on a
 * resource path. with specific parameters, body, http method, etc.
 * 
 * Two operations are considered equal if they have the same combination of Path
 * and HttMethod, regardless of their other attributes values.
 * 
 */
public class Operation {

    private HttpMethod httpMethod;
    private String description;
    private ConcurrentMap<String, Parameter> queryParameters;
    private ConcurrentMap<String, Parameter> pathParameters;
    private ConcurrentMap<String, Parameter> headerParameters;
    private Body requestBody = new Body();
    private Body responseBody = new Body();
    private List<String> acceptedContentTypes;

    private List<String> producedContentTypes;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Operation(HttpMethod method, String _path) {
        httpMethod = Validate.notNull(method);
        path = Validate.notNull(_path);

        queryParameters = new ConcurrentHashMap<String, Parameter>(8, 0.9f, 1);
        pathParameters = new ConcurrentHashMap<String, Parameter>(8, 0.9f, 1);
        headerParameters = new ConcurrentHashMap<String, Parameter>(8, 0.9f, 1);
        acceptedContentTypes = new ArrayList<String>();
        producedContentTypes = new ArrayList<String>();

    }

    public Set<Parameter> getQueryParameters() {
        return ImmutableSet.copyOf(queryParameters.values());
    }

    public Set<Parameter> getPathParameters() {
        return ImmutableSet.copyOf(pathParameters.values());
    }

    public Set<Parameter> getHeaderParameters() {
        return ImmutableSet.copyOf(headerParameters.values());
    }

    public Body getRequestBody() {
        return requestBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAcceptedContentTypes() {
        return acceptedContentTypes;
    }

    public void setAcceptedContentTypes(List<String> acceptedContentTypes) {
        this.acceptedContentTypes = new ArrayList<String>(acceptedContentTypes);
    }

    public List<String> getProducedContentTypes() {
        return producedContentTypes;
    }

    public void setProducedContentTypes(List<String> producedContentTypes) {
        this.producedContentTypes = new ArrayList<String>(producedContentTypes);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameter queryParameter(String name) {
        return getOrCreateParam(name, queryParameters);
    }

    private Parameter getOrCreateParam(String name, ConcurrentMap<String, Parameter> paramMap) {
        Parameter result = paramMap.get(name);
        if (result == null) {
            Parameter newParam = new Parameter(name);
            result = paramMap.putIfAbsent(name, newParam);
            if (result == null) {
                result = newParam;
            }
        }
        return result;
    }

    public Parameter pathParameter(String name) {
        return getOrCreateParam(name, pathParameters);
    }

    public Parameter headerParameter(String name) {
        return getOrCreateParam(name, headerParameters);
    }

    public void setRequestBody(Body requestBody) {
        this.requestBody = requestBody;
    }

    public Body getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Body responseBody) {
        this.responseBody = responseBody;
    }
}

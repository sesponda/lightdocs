package com.github.lightdocs.examples.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyResponse {
    private List<Object> fields = new ArrayList<Object>();
    public DummyResponse(Object... objects)  {
        fields.addAll(Arrays.asList(objects));
    }
    public List<Object> getFields() {
        return fields;
    }
}

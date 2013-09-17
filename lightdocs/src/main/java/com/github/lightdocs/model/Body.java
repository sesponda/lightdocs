package com.github.lightdocs.model;

/**
 * Used to describe the expected body contents in a request or a response, if
 * any.
 * 
 */
public class Body {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String example;

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

}

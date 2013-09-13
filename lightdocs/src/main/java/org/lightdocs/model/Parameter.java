package org.lightdocs.model;

/**
 * @author sesponda
 *
 */
public class Parameter {

    private String name;
    private String description;
    private String validationRules;
    private boolean required = false;
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Parameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getValidationRules() {
        return validationRules;
    }

    public boolean isRequired() {
        return required;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValidationRules(String validationRules) {
        this.validationRules = validationRules;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}

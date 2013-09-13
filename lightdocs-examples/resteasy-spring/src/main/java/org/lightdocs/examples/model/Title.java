package org.lightdocs.examples.model;

public class Title {
    private String id;
    private int releaseYear;
    private String name;
    private String alternativeNames;
    private int runtimeMins;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAlternativeNames() {
        return alternativeNames;
    }
    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }
    public int getRuntimeMins() {
        return runtimeMins;
    }
    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

}

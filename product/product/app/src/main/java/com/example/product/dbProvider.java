package com.example.product;

public class dbProvider {
    private String name;
    private String reference;
    private String type;
    private String date;
    private String time;
    private String location;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public dbProvider (String name, String reference, String type, String date, String time, String location, String description)
    {
        this.name = name;
        this.reference = reference;
        this.type = type;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }
}

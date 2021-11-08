package org.acme;


import java.util.Objects;

public class FormsService {

    private String name;
    private String formData;
    private String id;

    public FormsService() {
    }

    public FormsService(String name, String formData) {
        this.name = name;
        this.formData = formData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return formData;
    }

    public void setDescription(String formData) {
        this.formData = formData;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FormsService)) {
            return false;
        }

        FormsService other = (FormsService) obj;

        return Objects.equals(other.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
package com.ttn.ecommerce.model;

import javax.validation.constraints.NotNull;

public class CategoryModel {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

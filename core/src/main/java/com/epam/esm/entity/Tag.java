package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Tag {

    private Integer id;
    @NotNull(message = "Required fields are missing")
    @NotBlank(message = "The field must have at least 1 non-whitespace character")
    private String name;

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

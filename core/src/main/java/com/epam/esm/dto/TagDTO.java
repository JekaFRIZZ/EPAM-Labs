package com.epam.esm.dto;

public class TagDTO {
    private Integer id;
    private String name;

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDTO() {
    }

    public TagDTO(String name) {
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
}

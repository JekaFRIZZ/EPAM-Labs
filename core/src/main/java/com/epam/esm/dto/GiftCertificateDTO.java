package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDTO {

    private Long id;
    @NotNull(message = "Required fields are missing")
    @NotBlank(message = "The field must have at least 1 non-whitespace character")
    private String name;
    @NotNull(message = "Required fields are missing")
    @NotBlank(message = "The field must have at least 1 non-whitespace character")
    private String description;
    @Min(value = 0)
    private Integer price;
    @Min(value = 1)
    private Long duration;
    private LocalDateTime createData;
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;

    public GiftCertificateDTO(Long id,
                              String name,
                              String description,
                              Integer price,
                              Long duration,
                              LocalDateTime createData,
                              LocalDateTime lastUpdateDate,
                              List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createData = createData;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateData() {
        return createData;
    }

    public void setCreateData(LocalDateTime createData) {
        this.createData = createData;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createData=" + createData +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}

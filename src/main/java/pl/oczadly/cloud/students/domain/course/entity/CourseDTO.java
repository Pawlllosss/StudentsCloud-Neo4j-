package pl.oczadly.cloud.students.domain.course.entity;

import javax.validation.constraints.NotBlank;

public class CourseDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

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
}

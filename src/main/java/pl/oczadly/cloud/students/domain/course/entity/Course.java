package pl.oczadly.cloud.students.domain.course.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Relationship(type = "ATTENDS", direction = Relationship.INCOMING)
    private List<CourseAttendee> courseAttendees;

    public Course() {
        courseAttendees = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id) &&
                name.equals(course.name) &&
                description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public void addCourseAttendee(CourseAttendee courseAttendee) {
        courseAttendees.add(courseAttendee);
    }

    public void removeCourseAttendee(CourseAttendee courseAttendee) {
        courseAttendees.remove(courseAttendee);
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

    public List<CourseAttendee> getCourseAttendees() {
        return courseAttendees;
    }

    public void setCourseAttendees(List<CourseAttendee> courseAttendees) {
        this.courseAttendees = courseAttendees;
    }
}

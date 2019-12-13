package pl.oczadly.cloud.students.domain.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import pl.oczadly.cloud.students.domain.student.entity.Student;

import java.util.Objects;

@RelationshipEntity(type = "ATTENDS")
public class CourseAttendee {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Student student;

    @EndNode
    @JsonIgnore
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseAttendee that = (CourseAttendee) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(student, that.student) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, course);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

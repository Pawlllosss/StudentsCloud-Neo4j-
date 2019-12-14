package pl.oczadly.cloud.students.domain.student.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.cloud.students.domain.course.boundary.CourseRestController;
import pl.oczadly.cloud.students.domain.student.entity.Student;
import pl.oczadly.cloud.students.domain.student.entity.StudentDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class StudentResourceAssembler implements ResourceAssembler<Student, Resource<Student>> {

    @Override
    public Resource<Student> toResource(Student student) {
        Long studentId = student.getId();

        Link updateLink = linkTo(methodOn(StudentRestController.class).updateStudent(studentId, new StudentDTO()))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(StudentRestController.class).deleteStudent(studentId))
                .withRel("delete");
        Link assignedCoursesLink = linkTo(methodOn(CourseRestController.class).getCoursesAssignedToStudent(studentId))
                .withRel("courses");

        return new Resource<>(student, updateLink, deleteLink, assignedCoursesLink);
    }
}

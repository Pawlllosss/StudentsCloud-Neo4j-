package pl.oczadly.cloud.students.domain.student.boundary;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.cloud.students.domain.student.entity.Student;

@Component
public class StudentResourceAssembler implements ResourceAssembler<Student, Resource<Student>> {

    @Override
    public Resource<Student> toResource(Student student) {
        return new Resource<>(student);
    }
}

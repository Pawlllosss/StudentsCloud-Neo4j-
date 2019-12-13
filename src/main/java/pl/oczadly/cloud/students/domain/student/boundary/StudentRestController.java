package pl.oczadly.cloud.students.domain.student.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.cloud.students.domain.student.control.StudentService;
import pl.oczadly.cloud.students.domain.student.entity.Student;
import pl.oczadly.cloud.students.domain.student.entity.StudentDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/student", produces = {"application/hal+json"})
public class StudentRestController {

    private StudentResourceAssembler resourceAssembler;
    private StudentService studentService;


    public StudentRestController(StudentResourceAssembler resourceAssembler, StudentService studentService) {
        this.resourceAssembler = resourceAssembler;
        this.studentService = studentService;
    }

    @PostMapping
    public Resource<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Student createdStudent = studentService.createStudent(studentDTO);
        return resourceAssembler.toResource(createdStudent);

    }

    @GetMapping
    public Resources<Resource<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return mapStudentsToResources(students);
    }

    private Resources<Resource<Student>> mapStudentsToResources(List<Student> students) {
        List<Resource<Student>> studentsMapped =  students.stream()
                .map(resourceAssembler::toResource)
                .collect(Collectors.toList());
        Link selfLink = linkTo(StudentRestController.class).withSelfRel();

        return new Resources<>(studentsMapped, selfLink);
    }
}

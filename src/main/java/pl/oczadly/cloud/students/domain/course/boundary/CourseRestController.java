package pl.oczadly.cloud.students.domain.course.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.cloud.students.domain.course.control.CourseService;
import pl.oczadly.cloud.students.domain.course.entity.Course;
import pl.oczadly.cloud.students.domain.course.entity.CourseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/course", produces = {"application/hal+json"})
public class CourseRestController {

    private CourseResourceAssembler resourceAssembler;
    
    private CourseService courseService;

    public CourseRestController(CourseResourceAssembler resourceAssembler, CourseService courseService) {
        this.resourceAssembler = resourceAssembler;
        this.courseService = courseService;
    }

    @PostMapping
    public Resource<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course createdCourse = courseService.createCourse(courseDTO);
        return resourceAssembler.toResource(createdCourse);

    }

    @GetMapping
    public Resources<Resource<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return mapCoursesToResources(courses);
    }

    private Resources<Resource<Course>> mapCoursesToResources(List<Course> courses) {
        List<Resource<Course>> studentsMapped = courses.stream()
                .map(resourceAssembler::toResource)
                .collect(Collectors.toList());
        Link selfLink = linkTo(CourseRestController.class).withSelfRel();

        return new Resources<>(studentsMapped, selfLink);
    }

    @PutMapping("/{courseId}/student/{studentId}")
    public Resource<Course> addCourseAttendee(@PathVariable Long courseId, @PathVariable Long studentId) {
        Course modifiedCourse = courseService.addStudentToCourse(courseId, studentId);
        return resourceAssembler.toResource(modifiedCourse);

    }
}

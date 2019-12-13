package pl.oczadly.cloud.students.domain.course.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.cloud.students.domain.course.entity.Course;
import pl.oczadly.cloud.students.domain.course.entity.CourseDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

    @Override
    public Resource<Course> toResource(Course course) {
        Long courseId = course.getId();

        Link updateLink = linkTo(methodOn(CourseRestController.class).updateCourse(courseId, new CourseDTO()))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(CourseRestController.class).deleteCourse(courseId))
                .withRel("delete");

        return new Resource<>(course, updateLink, deleteLink);
    }
}

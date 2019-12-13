package pl.oczadly.cloud.students.domain.course.control;

import pl.oczadly.cloud.students.domain.course.entity.Course;
import pl.oczadly.cloud.students.domain.course.entity.CourseDTO;

import java.util.List;

public interface CourseService {

    Course createCourse(CourseDTO courseDTO);

    List<Course> getAllCourses();

    Course getCourseById(Long courseId);

    Course addStudentToCourse(Long courseId, Long studentId);

}

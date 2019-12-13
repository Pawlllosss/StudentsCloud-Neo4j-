package pl.oczadly.cloud.students.domain.course.control;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.oczadly.cloud.students.domain.course.entity.Course;
import pl.oczadly.cloud.students.domain.course.entity.CourseAttendee;
import pl.oczadly.cloud.students.domain.course.entity.CourseDTO;
import pl.oczadly.cloud.students.domain.student.control.StudentService;
import pl.oczadly.cloud.students.domain.student.entity.Student;

import java.util.LinkedList;
import java.util.List;

@Service
public class CourseServiceImplementation implements CourseService {

    private CourseRepository courseRepository;

    private StudentService studentService;

    private ModelMapper modelMapper;

    public CourseServiceImplementation(CourseRepository courseRepository, StudentService studentService, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> students = new LinkedList<>();
        Iterable<Course> retrievedCourses = courseRepository.findAll();
        retrievedCourses.forEach(students::add);

        return students;
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new IllegalStateException("Course not found"));
    }

    @Override
    public Course addStudentToCourse(Long courseId, Long studentId) {
        Course course = getCourseById(courseId);
        Student student = studentService.getStudentById(studentId);
        CourseAttendee courseAttendee = createCourseAttendee(course, student);

        course.addCourseAttendee(courseAttendee);

        return courseRepository.save(course);
    }

    private CourseAttendee createCourseAttendee(Course course, Student student) {
        CourseAttendee courseAttendee = new CourseAttendee();
        courseAttendee.setCourse(course);
        courseAttendee.setStudent(student);

        return courseAttendee;
    }
}

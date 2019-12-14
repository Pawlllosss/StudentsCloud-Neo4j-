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
import java.util.stream.Collectors;

@Service
public class CourseServiceImplementation implements CourseService {

    private CourseRepository courseRepository;
    private CourseAttendeeRepository courseAttendeeRepository;

    private StudentService studentService;

    private ModelMapper modelMapper;

    public CourseServiceImplementation(CourseRepository courseRepository, CourseAttendeeRepository courseAttendeeRepository,
                                       StudentService studentService, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.courseAttendeeRepository = courseAttendeeRepository;
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
    public List<Course> getCoursesAssignedToStudent(Long studentId) {
        List<CourseAttendee> courseAttendees = new LinkedList<>();
        courseAttendeeRepository.findAll()
                .forEach(courseAttendees::add);
        List<CourseAttendee> courseAttendeesRelatedToStudent = courseAttendees.stream()
                .filter(attendee -> isCourseAttendingRelatedToStudent(attendee, studentId))
                .collect(Collectors.toList());

        return courseAttendeesRelatedToStudent.stream()
                    .map(CourseAttendee::getCourse)
                    .collect(Collectors.toList());
    }

    private boolean isCourseAttendingRelatedToStudent(CourseAttendee attendee, Long studentId) {
        return attendee.getStudent().getId().equals(studentId);
    }

    @Override
    public Course updateCourseById(Long courseId, CourseDTO courseDTO) {
        Course course = getCourseById(courseId);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());

        return courseRepository.save(course);
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

    @Override
    public Course removeStudentFromCourse(Long courseId, Long studentId) {
        List<CourseAttendee> courseAttendees = new LinkedList<>();
        courseAttendeeRepository.findAll()
                .forEach(courseAttendees::add);
        CourseAttendee courseAttendee = courseAttendees.stream()
                .filter(attendee -> isCourseAttendingRelatedToCourseAndStudent(courseId, studentId, attendee))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No course assigned for this student"));

        Course course = getCourseById(courseId);
        course.removeCourseAttendee(courseAttendee);

        return courseRepository.save(course);
    }

    private boolean isCourseAttendingRelatedToCourseAndStudent(Long courseId, Long studentId, CourseAttendee attendee) {
        return attendee.getCourse().getId().equals(courseId) && attendee.getStudent().getId().equals(studentId);
    }

    @Override
    public void deleteCourseById(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new IllegalStateException("Course does not exist");
        }

        courseRepository.deleteById(courseId);
    }
}

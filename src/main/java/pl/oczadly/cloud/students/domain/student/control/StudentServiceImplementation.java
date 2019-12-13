package pl.oczadly.cloud.students.domain.student.control;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.oczadly.cloud.students.domain.student.entity.Student;
import pl.oczadly.cloud.students.domain.student.entity.StudentDTO;

import java.util.LinkedList;
import java.util.List;

@Service
public class StudentServiceImplementation implements StudentService {

    private StudentRepository studentRepository;
    private ModelMapper mapper;

    public StudentServiceImplementation(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public Student createStudent(StudentDTO studentDTO) {
        Student student = mapper.map(studentDTO, Student.class);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new LinkedList<>();
        Iterable<Student> retrievedStudents = studentRepository.findAll();
        retrievedStudents.forEach(students::add);

        return students;
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student does not exist"));
    }

    @Override
    public Student updateStudentById(Long studentId, StudentDTO studentDTO) {
        Student student = getStudentById(studentId);
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("Student does not exist");
        }

        studentRepository.deleteById(studentId);
    }
}

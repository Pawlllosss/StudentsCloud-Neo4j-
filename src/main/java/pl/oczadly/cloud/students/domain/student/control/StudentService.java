package pl.oczadly.cloud.students.domain.student.control;

import pl.oczadly.cloud.students.domain.student.entity.Student;
import pl.oczadly.cloud.students.domain.student.entity.StudentDTO;

import java.util.List;

public interface StudentService {

    Student createStudent(StudentDTO studentDTO);

    List<Student> getAllStudents();

    Student getStudentById(Long studentId);

    Student updateStudentById(Long studentId, StudentDTO studentDTO);

    void deleteStudentById(Long studentId);
}

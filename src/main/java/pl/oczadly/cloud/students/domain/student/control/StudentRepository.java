package pl.oczadly.cloud.students.domain.student.control;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.oczadly.cloud.students.domain.student.entity.Student;

public interface StudentRepository extends Neo4jRepository<Student, Long> {
}

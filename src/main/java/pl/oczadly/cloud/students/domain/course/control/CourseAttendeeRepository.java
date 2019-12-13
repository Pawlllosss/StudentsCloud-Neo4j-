package pl.oczadly.cloud.students.domain.course.control;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.oczadly.cloud.students.domain.course.entity.CourseAttendee;

import java.util.Optional;

public interface CourseAttendeeRepository extends Neo4jRepository<CourseAttendee, Long> {
}

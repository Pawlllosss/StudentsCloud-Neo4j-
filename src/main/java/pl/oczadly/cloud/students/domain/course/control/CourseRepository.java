package pl.oczadly.cloud.students.domain.course.control;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import pl.oczadly.cloud.students.domain.course.entity.Course;

@Repository
public interface CourseRepository extends Neo4jRepository<Course, Long> {
}

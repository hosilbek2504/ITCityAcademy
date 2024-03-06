package it.city.itcityacademy.repository;

import it.city.itcityacademy.entity.Group;
import it.city.itcityacademy.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByGroup(Group group);
}

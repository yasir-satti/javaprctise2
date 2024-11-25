package dev.yasir.javapractise2.repository;

import dev.yasir.javapractise2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {
}

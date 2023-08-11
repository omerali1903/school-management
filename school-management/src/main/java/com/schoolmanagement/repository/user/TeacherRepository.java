package com.schoolmanagement.repository.user;

import com.schoolmanagement.entity.concretes.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    Teacher findByUsernameEquals(String username);

    List<Teacher> getTeachersByNameContaining(String teacherName);

    Teacher getTeacherByUsername(String username);

}
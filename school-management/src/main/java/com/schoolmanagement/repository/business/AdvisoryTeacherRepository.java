package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvisoryTeacherRepository extends JpaRepository<AdvisoryTeacher, Long> {
    Optional<AdvisoryTeacher> getAdvisoryTeacherByTeacher_Id(Long teacherId);

    Optional<AdvisoryTeacher> findByTeacher_UsernameEquals(String username);

}
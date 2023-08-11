package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {
    List<Meet> findByStudentList_IdEquals(Long studentId);

    List<Meet> getByAdvisoryTeacher_IdEquals(Long id);

    Page<Meet> findByAdvisoryTeacher_IdEquals(Long id, Pageable pageable);
}
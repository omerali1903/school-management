package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.StudentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {
    List<StudentInfo> getAllByStudentId_Id(Long studentId);

    boolean existsByIdEquals(Long id);

    @Query("SELECT s FROM StudentInfo s WHERE s.teacher.username= ?1")
    Page<StudentInfo> findByTeacherId_UsernameEquals(String username, Pageable pageable);

    @Query("SELECT s FROM StudentInfo s WHERE s.student.username= ?1")
    Page<StudentInfo> findByStudentId_UsernameEquals(String username, Pageable pageable);

    @Query("SELECT (count (s)>0) FROM StudentInfo s WHERE s.student.id= ?1")
    boolean existsByStudent_IdEquals(Long studentId);

    @Query("SELECT s FROM StudentInfo s WHERE s.student.id= ?1")
    List<StudentInfo> findByStudent_IdEquals(Long studentId);

}
package com.schoolmanagement.repository.user;

import com.schoolmanagement.entity.concretes.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
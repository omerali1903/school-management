package com.schoolmanagement.repository.user;

import com.schoolmanagement.entity.concretes.user.ViceDean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViceDeanRepository extends JpaRepository<ViceDean, Long> {
    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);

    ViceDean findByUsernameEquals(String username);
}
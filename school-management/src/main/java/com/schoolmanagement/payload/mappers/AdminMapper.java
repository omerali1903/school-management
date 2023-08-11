package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.Admin;
import com.schoolmanagement.payload.request.AdminRequest;
import com.schoolmanagement.payload.response.AdminResponse;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    //!!! DTO --> POJO
    public Admin mapAdminRequestToAdmin(AdminRequest adminRequest) {
        return Admin.builder()
                .username(adminRequest.getUsername())
                .name(adminRequest.getName())
                .surname(adminRequest.getSurname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthDay(adminRequest.getBirthDay())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender())
                .build();
    }

    // !!! POJO --> DTO
    public AdminResponse mapAdminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .birthDay(admin.getBirthDay())
                .birthPlace(admin.getBirthPlace())
                .ssn(admin.getSsn())
                .build();
    }
}
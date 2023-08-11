package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.Dean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.request.DeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.service.user.UserRoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class DeanMapper {

    private final UserRoleService userRoleService;

    // !!! DTO --> POJO
    public Dean mapDeanRequestToDean(DeanRequest deanRequest){
        return Dean.builder()
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthDay(deanRequest.getBirthDay())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();
    }

    // !!! POJO --> DTO
    public DeanResponse mapDeanToDeanResponse(Dean dean){
        return DeanResponse.builder()
                .userId(dean.getId())
                .username(dean.getUsername())
                .name(dean.getName())
                .surname(dean.getSurname())
                .birthDay(dean.getBirthDay())
                .birthPlace(dean.getBirthPlace())
                .phoneNumber(dean.getPhoneNumber())
                .gender(dean.getGender())
                .ssn(dean.getSsn())
                .build();
    }

    // !!! DTO --> POJO ( Update )
    public Dean mapDeanRequestToUpdatedDean(DeanRequest deanRequest, Long managerId) {

        return Dean.builder()
                .id(managerId)
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthDay(deanRequest.getBirthDay())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.MANAGER))
                .build();

    }
}
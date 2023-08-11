package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.ViceDean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.request.ViceDeanRequest;
import com.schoolmanagement.payload.response.ViceDeanResponse;
import com.schoolmanagement.service.user.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ViceDeanMapper {

    private final UserRoleService userRoleService;

    // !!! DTO --> POJO
    public ViceDean mapViceDeanRequestToViceDean(ViceDeanRequest viceDeanRequest){
        return ViceDean.builder()
                .birthDay(viceDeanRequest.getBirthDay())
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }


    // !!! POJO --> DTO
    public ViceDeanResponse mapViceDeanToViceDeanResponse(ViceDean viceDean) {
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .username(viceDean.getUsername())
                .name(viceDean.getName())
                .surname(viceDean.getSurname())
                .birthPlace(viceDean.getBirthPlace())
                .birthDay(viceDean.getBirthDay())
                .phoneNumber(viceDean.getPhoneNumber())
                .ssn(viceDean.getSsn())
                .gender(viceDean.getGender())
                .build();
    }

    // !!! Update islemi icin gerekli mapper metodu
    public ViceDean mapViceDeanRequestToUpdatedViceDean(ViceDeanRequest viceDeanRequest, Long viceDeanId){
        return ViceDean.builder()
                .id(viceDeanId)
                .username(viceDeanRequest.getUsername())
                .ssn(viceDeanRequest.getSsn())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .birthDay(viceDeanRequest.getBirthDay())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER))
                .build();
    }

}

package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.business.EducationTerm;
import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EducationTermMapper {

    // !!! DTO --> POJO
    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest) {
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    // !!! POJO --> DTO
    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm educationTerm){
        return EducationTermResponse.builder()
                .id(educationTerm.getId())
                .term(educationTerm.getTerm())
                .startDate(educationTerm.getStartDate())
                .endDate(educationTerm.getEndDate())
                .lastRegistrationDate(educationTerm.getLastRegistrationDate())
                .build();
    }

    // !!! Update icin DTO --> POJO
    public EducationTerm mapEducationTermRequestToUpdatedEducationTerm(Long id, EducationTermRequest educationTermRequest){
        return mapEducationTermRequestToEducationTerm(educationTermRequest)
                .toBuilder()
                .id(id)
                .build();
    }
}
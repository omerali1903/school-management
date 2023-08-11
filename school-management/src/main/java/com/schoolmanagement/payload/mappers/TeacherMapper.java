package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.Teacher;
import com.schoolmanagement.payload.request.TeacherRequest;
import com.schoolmanagement.payload.response.TeacherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeacherMapper {

    // !!! DTO --> POJO
    public Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest) {

        return Teacher.builder()
                .name(teacherRequest.getName())
                .surname(teacherRequest.getSurname())
                .ssn(teacherRequest.getSsn())
                .username(teacherRequest.getUsername())
                .birthDay(teacherRequest.getBirthDay())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .email(teacherRequest.getEmail())
                .isAdvisor(teacherRequest.isAdvisorTeacher())
                .gender(teacherRequest.getGender())
                .build();
    }


    // !!! POJO --> DTO
    public TeacherResponse mapTeacherToTeacherResponse(Teacher teacher){
        return TeacherResponse.builder()
                .userId(teacher.getId())
                .username(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }


    // !!! ( UPDATE ) DTO --> POJO
    public Teacher mapTeacherRequestToUpdatedTeacher(TeacherRequest teacherRequest, Long id){
        Teacher teacher =  mapTeacherRequestToTeacher(teacherRequest);
        teacher.setId(id);
        return teacher ;
    }
}

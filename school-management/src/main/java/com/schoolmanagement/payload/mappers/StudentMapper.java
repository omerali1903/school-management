package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.payload.request.StudentRequest;
import com.schoolmanagement.payload.response.StudentResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StudentMapper {

    // !!! DTO --> POJO
    public Student mapStudentRequestToStudent(StudentRequest studentRequest){

        return Student.builder()
                .fatherName(studentRequest.getFatherName())
                .motherName(studentRequest.getMotherName())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .email(studentRequest.getEmail())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .build();
    }
    //!!! POJO --> DTO
    public StudentResponse mapStudentToStudentResponse(Student student){

        return StudentResponse.builder()
                .userId(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .birthPlace(student.getBirthPlace())
                .birthDay(student.getBirthDay())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .isActive(student.isActive())
                .ssn(student.getSsn())
                .studentNumber(student.getStudentNumber())
                .build();
    }

    // Update ( DTO --> POJO )
    public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest, Long studentId) {

        Student student = mapStudentRequestToStudent(studentRequest);
        student.setId(studentId);
        return student ;
    }
}
package com.schoolmanagement.service.validator;

import com.schoolmanagement.entity.abstracts.User;

import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.entity.concretes.user.Teacher;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.messages.ErrorMessages;

import com.schoolmanagement.payload.request.StudentRequest;
import com.schoolmanagement.payload.request.TeacherRequest;
import com.schoolmanagement.payload.request.abstracts.BaseUserRequest;
import com.schoolmanagement.repository.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final AdminRepository adminRepository;
    private final DeanRepository deanRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ViceDeanRepository viceDeanRepository;

    public void checkUniqueProperties(User user, BaseUserRequest baseUserRequest){

        String updatedUsername = "";
        String updatedSsn = "";
        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanged = false ;

        if(!user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername())) {
            updatedUsername = baseUserRequest.getUsername();
            isChanged = true ;
        }

        if(!user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())){
            updatedSsn = baseUserRequest.getSsn();
            isChanged = true;
        }

        if(!user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())){
            updatedPhone = baseUserRequest.getPhoneNumber();
            isChanged = true;
        }

        boolean teacherOrStudent = false ;

        if(user instanceof Teacher && baseUserRequest instanceof TeacherRequest){
            Teacher teacher = (Teacher) user;
            TeacherRequest teacherRequest = (TeacherRequest) baseUserRequest;
            teacherOrStudent = true;
            if(!teacher.getEmail().equalsIgnoreCase(teacherRequest.getEmail())){
                updatedEmail = teacherRequest.getEmail();
                isChanged = true;
            }
        }

        if(user instanceof Student && baseUserRequest instanceof StudentRequest){
            Student student = (Student) user;
            StudentRequest studentRequest = (StudentRequest) baseUserRequest;
            teacherOrStudent = true;
            if(!student.getEmail().equalsIgnoreCase(studentRequest.getEmail())){
                updatedEmail = studentRequest.getEmail();
                isChanged = true;
            }

        }
        if (isChanged) {
            if(teacherOrStudent) {
                checkDuplicate(updatedUsername,updatedSsn,updatedPhone,updatedEmail);
            } else {
                checkDuplicate(updatedUsername,updatedSsn,updatedPhone);
            }
        }
    }


    public void checkDuplicate(String... values) {

        String username = values[0];
        String ssn = values[1];
        String phone = values[2];
        String email = "";

        if(values.length==4){
            email= values[3];
        }

        if(adminRepository.existsByUsername(username) || deanRepository.existsByUsername(username)
                || studentRepository.existsByUsername(username) || teacherRepository.existsByUsername(username)
                || viceDeanRepository.existsByUsername(username))
        {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        } else if (adminRepository.existsBySsn(ssn) || deanRepository.existsBySsn(ssn) || studentRepository.existsBySsn(ssn)
                || teacherRepository.existsBySsn(ssn) || viceDeanRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone) || deanRepository.existsByPhoneNumber(phone) || studentRepository.existsByPhoneNumber(phone)
                || teacherRepository.existsByPhoneNumber(phone) || viceDeanRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER,phone));
        } else if (studentRepository.existsByEmail(email) || teacherRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL,email));
        }


    }
}
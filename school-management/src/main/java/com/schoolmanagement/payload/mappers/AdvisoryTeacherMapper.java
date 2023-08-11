package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.schoolmanagement.entity.concretes.user.Teacher;
import com.schoolmanagement.payload.response.AdvisoryTeacherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvisoryTeacherMapper {

    // !!! Teacher --> AdvisoryTeacher
    public AdvisoryTeacher mapTeacherToAdvisoryTeacher(Teacher teacher){
        return  AdvisoryTeacher.builder()
                .teacher(teacher)
                .build();
    }

    // !!! AdvisoryTeacher --> AdvisoryTeacherDTO
    public AdvisoryTeacherResponse mapAdvisorTeacherToAdvisorTeacherResponse(AdvisoryTeacher advisoryTeacher){
        return AdvisoryTeacherResponse.builder()
                .advisorTeacherId(advisoryTeacher.getId())
                .teacherName(advisoryTeacher.getTeacher().getName())
                .teacherSurname(advisoryTeacher.getTeacher().getSurname())
                .teacherSSN(advisoryTeacher.getTeacher().getSsn())
                .build();

    }
}
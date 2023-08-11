package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.business.EducationTerm;
import com.schoolmanagement.entity.concretes.business.Lesson;
import com.schoolmanagement.entity.concretes.business.LessonProgram;
import com.schoolmanagement.payload.request.LessonProgramRequest;
import com.schoolmanagement.payload.response.LessonProgramResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
public class LessonProgramMapper {

    // !!! DTO --> POJO
    public LessonProgram mapLessonProgramRequestToLessonProgram(LessonProgramRequest lessonProgramRequest,
                                                                Set<Lesson> lessonSet,
                                                                EducationTerm educationTerm){


        return LessonProgram.builder()
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .day(lessonProgramRequest.getDay())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();

    }


    // !!! POJO --> DTO
    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram){

        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLessons())
                .build();
    }
}
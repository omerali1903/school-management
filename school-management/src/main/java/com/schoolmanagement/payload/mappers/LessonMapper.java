package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.business.Lesson;
import com.schoolmanagement.payload.request.LessonRequest;
import com.schoolmanagement.payload.response.LessonResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonMapper {

    // !!! DTO --> POJO
    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){

        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    // !!! POJO --> DTO
    public LessonResponse mapLessonToLessonResponse(Lesson lesson){

        return LessonResponse.builder()
                .lessonId(lesson.getLessonId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }
}
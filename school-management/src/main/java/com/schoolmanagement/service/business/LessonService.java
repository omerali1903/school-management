package com.schoolmanagement.service.business;

import com.schoolmanagement.entity.concretes.business.Lesson;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.LessonMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.LessonRequest;
import com.schoolmanagement.payload.response.LessonResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.LessonRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final PageableHelper pageableHelper;

    // Not: save() *******************************************************************************
    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
        // !!! conflict kontrolu ( lesson name ile )
        isLessonExistByLessonName(lessonRequest.getLessonName());
        // !!! DTO --> POJO
        Lesson savedLesson = lessonRepository.save(lessonMapper.mapLessonRequestToLesson(lessonRequest));

        return ResponseMessage.<LessonResponse>builder()
                .object(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .message(SuccessMessages.LESSON_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    private boolean isLessonExistByLessonName(String lessonName){

        boolean lessonExist =  lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);

        if(lessonExist){
            throw new ConflictException(String.format(ErrorMessages.LESSON_ALREADY_EXIST, lessonName));
        } else {
            return false;
        }

    }

    // Not: deleteById() *********************************************************************
    public ResponseMessage deleteLessonById(Long id) {

        isLessonExistById(id);
        lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Lesson isLessonExistById(Long id){

        return lessonRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, id)));
    }

    // Not: getByName() ************************************************************************
    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {

        if(lessonRepository.getLessonByLessonName(lessonName).isPresent()){
            return ResponseMessage.<LessonResponse>builder()
                    .message(SuccessMessages.LESSON_FOUND)
                    .object(lessonMapper.mapLessonToLessonResponse(lessonRepository.getLessonByLessonName(lessonName).get()))
                    .build();
        } else {
            return ResponseMessage.<LessonResponse>builder()
                    .message(ErrorMessages.NOT_FOUND_LESSON_MESSAGE)
                    .build();
        }
    }

    // Not: getAllWithPage() *********************************************************************
    public Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return lessonRepository.findAll(pageable).map(lessonMapper::mapLessonToLessonResponse);
    }

    // Not: getLessonsByIdList() *******************************************************************
    public Set<Lesson> getLessonByLessonIdSet(Set<Long> idSet) {


        //  return lessonRepository.getLessonByLessonIdList(lessons);

        return idSet.stream()
                .map(this::isLessonExistById)
                .collect(Collectors.toSet());

    }



}
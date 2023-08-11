package com.schoolmanagement.controller.business;

import com.schoolmanagement.entity.concretes.business.Lesson;
import com.schoolmanagement.payload.request.LessonRequest;
import com.schoolmanagement.payload.response.LessonResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Not: save() *************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PostMapping("/save") // http://localhost:8080/lessons/save   + POST
    public ResponseMessage<LessonResponse> saveLesson(@RequestBody @Valid LessonRequest lessonRequest){

        return lessonService.saveLesson(lessonRequest);
    }
    // Not: deleteById() *********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}") // http://localhost:8080/lessons/delete/2  + DELETE
    public ResponseMessage deleteLessonById(@PathVariable Long id){

        return lessonService.deleteLessonById(id);
    }
    // Not: getByName() ************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getLessonByName/{lessonName}")  // http://localhost:8080/lessons/getLessonByName/Math + GET
    public ResponseMessage<LessonResponse> getLessonByName(@PathVariable String lessonName){

        return lessonService.getLessonByLessonName(lessonName);
    }
    // Not: getAllWithPage() *********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/findLessonByPage")  // http://localhost:8080/lessons/findLessonByPage  + GET
    public Page<LessonResponse> findLessonByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return lessonService.findLessonByPage(page,size,sort,type);
    }
    // Not: getLessonsByIdList() *******************************************************************
    // TODO Lesson --> LessonResponse
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllLessonByLessonId") // // http://localhost:8080/lessons/getAllLessonByLessonId?lessonId=1,2,3 + GET
    public Set<Lesson> getAllLessonByLessonId(@RequestParam(name="lessonId") Set<Long> idSet){

        return lessonService.getLessonByLessonIdSet(idSet);
    }


}
package com.schoolmanagement.controller.business;

import com.schoolmanagement.payload.request.LessonProgramRequest;
import com.schoolmanagement.payload.response.LessonProgramResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    // Not :  Save() *********************************************************
    @PostMapping("/save") // http://localhost:8080/lessonPrograms/save   + POST  + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid LessonProgramRequest lessonProgramRequest){

        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }
    // Not : getAll() **********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','STUDENT','TEACHER')")
    @GetMapping("/getAll") // http://localhost:8080/lessonPrograms/getAll   + GET
    public List<LessonProgramResponse> getAllLessonProgramByList(){

        return lessonProgramService.getAllLessonProgramByList();
    }

    // Not : getById() *********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getById/{id}")  // http://localhost:8080/lessonPrograms/getById/1  +  GET
    public LessonProgramResponse getLessonProgramById(@PathVariable Long id) {

        return  lessonProgramService.getLessonProgramById(id);
    }

    // Not : getAllLessonProgramUnassigned() ************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','STUDENT','TEACHER')")
    @GetMapping("/getAllUnassigned")  // http://localhost:8080/lessonPrograms/getAllUnassigned  + GET
    public List<LessonProgramResponse> getAllUnassigned(){

        return lessonProgramService.getAllLessonProgramUnassigned();
    }


    // Not : getAllLessonProgramAssigned() **************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','STUDENT','TEACHER')")
    @GetMapping("/getAllAssigned")  // http://localhost:8080/lessonPrograms/getAllAssigned  + GET
    public List<LessonProgramResponse> getAllAssigned(){

        return lessonProgramService.getAllAssigned();
    }

    // Not : Delete() ***********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")  // http://localhost:8080/lessonPrograms/delete/2  + DELETE
    public ResponseMessage deleteLessonProgramById(@PathVariable Long id){
        return lessonProgramService.deleteLessonProgramById(id);
    }

    // Not :  getAllWithPage() ***************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','STUDENT','TEACHER')")
    @GetMapping("/getAllLessonProgramByPage") // http://localhost:8080/lessonPrograms/getAllLessonProgramByPage?page=0&size=1&sort=id&type=desc
    public Page<LessonProgramResponse> getAllLessonProgramByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return lessonProgramService.getAllLessonProgramByPage(page,size,sort,type);
    }

    // Not : getLessonProgramByTeacher() *****************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllLessonProgramByTeacher") // http://localhost:8080/lessonPrograms/getAllLessonProgramByTeacher  + GET
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherUserName(HttpServletRequest httpServletRequest){

        return lessonProgramService.getAllLessonProgramByTeacher(httpServletRequest);
    }

    // Not :  getLessonProgramByStudent() *****************************************************
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllLessonProgramByStudent") // http://localhost:8080/lessonPrograms/getAllLessonProgramByStudent  + GET
    public Set<LessonProgramResponse> getAllLessonProgramByStudent(HttpServletRequest httpServletRequest){
        return lessonProgramService.getAllLessonProgramByStudent(httpServletRequest);
    }
}

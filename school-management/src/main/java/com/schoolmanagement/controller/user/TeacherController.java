package com.schoolmanagement.controller.user;

import com.schoolmanagement.payload.request.ChooseLessonTeacherRequest;
import com.schoolmanagement.payload.request.TeacherRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.TeacherResponse;
import com.schoolmanagement.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    // Not :  Save() *********************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PostMapping("/save") // http://localhost:8080/teachers/save  + POST
    public ResponseMessage<TeacherResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {

        return teacherService.saveTeacher(teacherRequest);
    }
    // Not : getAll() ***********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAll") // http://localhost:8080/teachers/getAll  + GET
    public List<TeacherResponse> getAllTeacher(){
        return teacherService.getAllTeacher();
    }
    // Not : getByName() ***********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getTeacherByName")  // http://localhost:8080/teachers/getTeacherByName?name=Mirac  + GET
    public List<TeacherResponse>  getTeacherByName(@RequestParam (name = "name") String teacherName){
        return teacherService.getTeacherByName(teacherName);
    }

    // Not : Delete() ************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")  // http://localhost:8080/teachers/delete/2   + DELETE
    public ResponseMessage deleteTeacherById(@PathVariable Long id){
        return teacherService.deleteTeacherById(id);
    }
    // Not : getById() **********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getSavedTeacherById/{id}")   // http://localhost:8080/teachers/getSavedTeacherById/1   + GET
    public ResponseMessage<TeacherResponse> findTeacherById(@PathVariable Long id){
        return teacherService.getTeacherById(id);
    }
    // Not: getAllWithPage ***********************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllTeacherByPage")  // http://localhost:8080/teachers/getAllTeacherByPage   + GET
    public Page<TeacherResponse>  getAllTeacherByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return teacherService.getAllTeacherByPage(page,size,sort,type);
    }

    // Not: Update() ***************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PutMapping("/update/{id}")  // http://localhost:8080/teachers/update/1   + PUT
    public ResponseMessage<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacherRequest,
                                                          @PathVariable Long userId) {
        return teacherService.updateTeacher(teacherRequest, userId);
    }
    // Not: addLessonProgramsToTeachersLessonsProgram() **********************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PostMapping("/chooseLesson")  // http://localhost:8080/teachers/chooseLesson  + POST
    public ResponseMessage<TeacherResponse> chooseLesson(@RequestBody @Valid ChooseLessonTeacherRequest chooseLessonTeacherRequest){

        return teacherService.chooseLesson(chooseLessonTeacherRequest);

    }






}
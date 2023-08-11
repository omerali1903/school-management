package com.schoolmanagement.controller.business;

import com.schoolmanagement.payload.request.StudentInfoRequest;
import com.schoolmanagement.payload.request.UpdateStudentInfoRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.StudentInfoResponse;
import com.schoolmanagement.service.business.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.http11.upgrade.UpgradeServletInputStream;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    // Not :  Save() **********************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @PostMapping("/save")  // http://localhost:8080/studentInfo/save  + POST
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid StudentInfoRequest studentInfoRequest ){
        return studentInfoService.saveStudentInfo(httpServletRequest,studentInfoRequest);
    }
    // Not : Delete() ************************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage delete(@PathVariable Long studentInfoId){
        return studentInfoService.deleteStudentInfo(studentInfoId);
    }
    // Not: getAllWithPage ***********************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllStudentInfoByPage")
    public Page<StudentInfoResponse> getAllStudentInfoByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return studentInfoService.getAllStudentInfoByPage(page,size,sort,type);
    }
    // Not: Update() *************************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    @PutMapping("/update/{studentInfo}")
    public ResponseMessage<StudentInfoResponse> update(@RequestBody @Valid  UpdateStudentInfoRequest updateStudentInfoRequest,
                                                       @PathVariable Long studentInfo){

        return studentInfoService.update(updateStudentInfoRequest, studentInfo);
    }
    // Not: getAllForTeacher() ****************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllForTeacher")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){

        return new ResponseEntity<>(studentInfoService.getAllForTeacher(httpServletRequest,page,size), HttpStatus.OK);
    }
    // Not: getAllForStudent() ****************************************************
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/getAllForStudent")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){

        return new ResponseEntity<>(studentInfoService.getAllForStudent(httpServletRequest,page,size), HttpStatus.OK);
    }

    // Not: getStudentInfoByStudentId() ********************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getByStudentId/{studentId}")
    public ResponseEntity<List<StudentInfoResponse>> getStudentInfoByStudentId(@PathVariable Long studentId){

        List<StudentInfoResponse> studentInfoResponse = studentInfoService.getStudentInfoByStudentId(studentId);
        return ResponseEntity.ok(studentInfoResponse);
    }

    // Not: getStudentInfoById() ***************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/get/{studentInfoId}")
    public ResponseEntity<StudentInfoResponse> getStudentInfoById(@PathVariable Long studentInfoId){
        return ResponseEntity.ok(studentInfoService.findStudentInfoById(studentInfoId));
    }
}
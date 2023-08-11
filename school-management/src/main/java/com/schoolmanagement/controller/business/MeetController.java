package com.schoolmanagement.controller.business;

import com.schoolmanagement.payload.request.MeetRequest;
import com.schoolmanagement.payload.response.MeetResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/meet")
@RequiredArgsConstructor
public class MeetController {

    private final MeetService meetService;

    // Not :  Save() **********************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest,
                                                  @RequestBody @Valid MeetRequest meetRequest ){

        return meetService.saveMeet(httpServletRequest, meetRequest);

    }
    // Not: getAll *************************************************************
    @PreAuthorize("hasAnyAuthority( 'ADMIN')")
    @GetMapping("/getAll")
    public List<MeetResponse> getAll(){
        return meetService.getAll();
    }
    // Not: getByMeetId ********************************************************
    @PreAuthorize("hasAnyAuthority( 'ADMIN')")
    @GetMapping("/getMeetById/{meetId}")
    public ResponseMessage<MeetResponse> getMeetById(@PathVariable Long meetId){

        return meetService.getMeetById(meetId);
    }
    // Not : Delete() ************************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN' )")
    @DeleteMapping("/delete/{meetId}")
    public ResponseMessage delete(@PathVariable Long meetId){
        return meetService.delete(meetId);
    }
    // Not: Update() *************************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN' )")
    @PutMapping("/update/{meetId}")
    public ResponseMessage<MeetResponse> updateMeet(@RequestBody @Valid MeetRequest meetRequest,
                                                    @PathVariable Long meetId){
        return meetService.updateMeet(meetRequest, meetId);
    }
    // Not: getAllByAdvTeacher() ************************************************
    @PreAuthorize("hasAnyAuthority('TEACHER' )")
    @GetMapping("/getAllByAdvTeacherAsList")
    public ResponseEntity<List<MeetResponse>> getAllByAdvTeacher(HttpServletRequest httpServletRequest){

        return meetService.getAllMeetByAdvTeacher(httpServletRequest);
    }
    // Not: getAllMeetByStudent() ************************************************
    @PreAuthorize("hasAnyAuthority('STUDENT' )")
    @GetMapping("/getAllMeetByStudent")
    public List<MeetResponse>  getAllMeetByStudent(HttpServletRequest httpServletRequest){
        return meetService.getAllMeetByStudent(httpServletRequest);
    }
    // Not: getAllWithPage ***********************************************************
    @PreAuthorize("hasAnyAuthority( 'ADMIN')")
    @GetMapping("/getAllWithPage")
    public Page<MeetResponse> getAllMeetByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){
        return meetService.getAllMeetByPage(page,size);
    }
    // Not: gettAllByAdvTeacherByPage() **********************************************
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @GetMapping("/getAllMeetByAdvisorAsPage")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){
        return meetService.getAllMeetByTeacher(httpServletRequest,page,size);
    }
}
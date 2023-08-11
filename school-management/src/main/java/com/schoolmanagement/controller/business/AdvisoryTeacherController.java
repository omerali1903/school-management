package com.schoolmanagement.controller.business;

import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.schoolmanagement.payload.response.AdvisoryTeacherResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.AdvisoryTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advisorTeacher")
public class AdvisoryTeacherController {

    private final AdvisoryTeacherService advisoryTeacherService;

    // Not : getAll() ****************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAll")
    public List<AdvisoryTeacherResponse> getAllAdvisorTeacher(){
        return advisoryTeacherService.getAll();
    }

    // Not: getAllWithPage ***********************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllAdvisorTeacherByPage")
    public Page<AdvisoryTeacherResponse> getAllAdvisorTeacherByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return advisoryTeacherService.getAllAdvisorTeacherByPage(page,size,sort,type);
    }

    // Not : Delete() ****************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteAdvisorTeacher(@PathVariable Long id) {
        return advisoryTeacherService.deleteAdvisorTeacherById(id);
    }


}
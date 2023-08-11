package com.schoolmanagement.controller.business;

import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;

    // Not: Save() ******************************************************************
    @PostMapping("/save") // http://localhost:8080/educationTerm/save  + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest){

        return educationTermService.saveEducationTerm(educationTermRequest);
    }

    // Not: getById() ***********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping("/{id}") // http://localhost:8080/educationTerm/1    + GET
    public EducationTermResponse getEducationTermById(@PathVariable Long id){
        return educationTermService.getEducationTermResponseById(id);
    }
    // Not: getAll() *************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping("/getAll") // http://localhost:8080/educationTerm/getAll    + GET
    public List<EducationTermResponse> getAllEducationTerms(){

        return educationTermService.getAllEducationTerms();
    }

    // Not: getAllWithPage() *****************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    @GetMapping("/getAllEducationTermsByPage") // http://localhost:8080/educationTerm/getAllEducationTermsByPage  + GET
    public Page<EducationTermResponse> getAllEducationTermsByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "startDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return educationTermService.getAllEducationTermsByPage(page,size,sort,type);
    }

    // Not: deleteById() *********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}") // http://localhost:8080/educationTerm/delete/2   + DELETE
    public ResponseMessage deleteEducationTermById(@PathVariable Long id){

        return educationTermService.deleteEducationTermById(id);
    }

    // Not: updateById() *********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @PutMapping("/update/{id}") // http://localhost:8080/educationTerm/update/2   + PUT
    public ResponseMessage<EducationTermResponse> updateEducationTermById(@PathVariable Long id,
                                                                          @RequestBody @Valid EducationTermRequest educationTermRequest) {
        return educationTermService.updateEducationTerm(id, educationTermRequest);
    }
}
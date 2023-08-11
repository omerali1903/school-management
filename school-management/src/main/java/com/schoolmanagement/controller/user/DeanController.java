package com.schoolmanagement.controller.user;

import com.schoolmanagement.payload.request.DeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.user.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dean")
@RequiredArgsConstructor

@PreAuthorize("hasAnyAuthority('ADMIN')")
public class DeanController {

    private final DeanService deanService;

    // Not: save() ***************************************************************
    @PostMapping("/save") // http://localhost:8080/dean/save  + POST
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest){

        return deanService.save(deanRequest);
    }

    // Not :  UpdateById() **********************************************************
    @PutMapping("/update/{userId}") // http://localhost:8080/dean/update/1  + PUT
    public ResponseMessage<DeanResponse> update(@RequestBody @Valid DeanRequest deanRequest, @PathVariable Long userId) {

        return deanService.update(deanRequest, userId);
    }
    // Not: Delete() *****************************************************************
    @DeleteMapping("/delete/{userId}") // http://localhost:8080/dean/delete/1  + DELETE
    public ResponseMessage<?> deleteDeanById(@PathVariable Long userId){

        return deanService.deleteDeanById(userId);
    }
    // Not :  getById() **************************************************************
    @GetMapping("/getManagerById/{userId}") // http://localhost:8080/dean/getManagerById/1 + Get
    public ResponseMessage<DeanResponse> getDeanById(@PathVariable Long userId){
        return deanService.getDeanById(userId);
    }

    // Not :  getAll() ***************************************************************
    @GetMapping("/getAll") // http://localhost:8080/dean/getAll + GET
    public List<DeanResponse> getAllDeans(){
        return deanService.getAllDeans();
    }

    // Not :  getAllDeansByPage() ****************************************************
    @GetMapping("/getAllDeansByPage") // http://localhost:8080/dean/getAllDeansByPage?page=0&size=10&sort=name&type=asc  + GET
    public Page<DeanResponse> getAllDeansByPage(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(defaultValue = "desc", value = "type") String type
    ){
        return deanService.getAllDeansByPage(page,size,sort,type);
    }


}
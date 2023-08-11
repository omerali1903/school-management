package com.schoolmanagement.contactmessage;


import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    // not: save()************************************************************
    @PostMapping("/save")  // http://localhost:8080/contactMessages/save    + POST
    public ResponseMessage<ContactMessageResponse> save(@RequestBody
                                                        @Valid ContactMessageRequest contactMessageRequest) {
        return contactMessageService.save(contactMessageRequest);
    }
    // not: getAll() ***********************************************************
    @GetMapping("/getAll") // http://localhost:8080/contactMessages/getAll + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<ContactMessageResponse> getAll( // getAll(int page, int size, Direction type)
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "sort", defaultValue = "date") String sort,
                                                @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.getAll(page,size,sort,type);
    }

    // not: searchByEmail() ****************************************************
    @GetMapping("/searchByEmail")  // http://localhost:8080/contactMessages/searchByEmail?email=xxx@yyy.com  +  GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.searchByEmail(email,page,size,sort,type);
    }

    // not: searchBySubject() **************************************************
    @GetMapping("/searchBySubject") // http://localhost:8080/contactMessages/searchBySubject?subject=deneme  + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.searchBySubject(subject,page,size,sort,type);
    }

}
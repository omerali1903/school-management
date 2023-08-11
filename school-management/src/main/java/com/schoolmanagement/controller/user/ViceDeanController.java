package com.schoolmanagement.controller.user;

import com.schoolmanagement.payload.request.ViceDeanRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.ViceDeanResponse;
import com.schoolmanagement.service.user.ViceDeanService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vicedean")
@RequiredArgsConstructor
public class ViceDeanController {

    private final ViceDeanService viceDeanService;

    // Not :  Save() *************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/save") // http://localhost:8080/vicedean/save  + POST
    public ResponseMessage<ViceDeanResponse> saveViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest){
        return viceDeanService.saveViceDean(viceDeanRequest);
    }
    // Not :  UpdateById() ********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/update/{userId}") // http://localhost:8080/vicedean/update/1  + PUT
    public ResponseMessage<ViceDeanResponse> updateViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest,
                                                            @PathVariable Long userId) {
        return viceDeanService.updateViceDean(viceDeanRequest, userId);
    }
    // Not :  Delete() *************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}") // http://localhost:8080/vicedean/delete/1 + DELETE
    public ResponseMessage deleteViceDeanByAdmin(@PathVariable Long userId) {

        return viceDeanService.deleteViceDeanByUserId(userId);
    }


    // Not :  getById() ************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getViceDeanById/{userId}") // http://localhost:8080/vicedean/getViceDeanById/1  + GET
    public ResponseMessage<ViceDeanResponse> findViceDeanByViceDeanId(@PathVariable Long userId){
        return viceDeanService.getViceDeanByViceDeanId(userId);
    }

    // Not :  getAll() *************************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getAll") // http://localhost:8080/vicedean/getAll  + GET
    public List<ViceDeanResponse> getAllViceDeans(){

        return viceDeanService.getAllViceDeans();
    }

    // Not :  getAllWithPage() ******************************************************************
    @GetMapping("/getAllViceDeanByPage") //  http://localhost:8080/vicedean/getAllViceDeanByPage + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<ViceDeanResponse> getAllViceDeanByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type" , defaultValue = "desc") String type
    ){
        return viceDeanService.getAllViceDeanByPage(page,size,sort,type);
    }



}
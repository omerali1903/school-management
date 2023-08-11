package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.concretes.user.Dean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.DeanMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.DeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.user.DeanRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import com.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeanService {

    private final DeanRepository deanRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final DeanMapper deanMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;

    // Not: save() ***************************************************************
    public ResponseMessage<DeanResponse> save(DeanRequest deanRequest) {

        // !!! Dublicate kontrolu
        uniquePropertyValidator.checkDuplicate(deanRequest.getUsername(), deanRequest.getSsn(), deanRequest.getPhoneNumber());
        // !!! DTO --> POJO
        Dean dean = deanMapper.mapDeanRequestToDean(deanRequest);
        dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        dean.setPassword(passwordEncoder.encode(dean.getPassword()));
        Dean savedDean =  deanRepository.save(dean);

        return ResponseMessage.<DeanResponse>builder()
                .message(SuccessMessages.DEAN_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(deanMapper.mapDeanToDeanResponse(savedDean))
                .build();

    }

    // Not :  UpdateById() **********************************************************
    public ResponseMessage<DeanResponse> update(DeanRequest deanRequest, Long deanId) {
        // !!! ya yoksa kontrolu ??
        Dean dean = isDeanExist(deanId);
        // !!! dublicate kontrolu
        uniquePropertyValidator.checkUniqueProperties(dean, deanRequest);
        // !!! DTO --> POJO
        Dean updatedDean = deanMapper.mapDeanRequestToUpdatedDean(deanRequest, deanId);
        // !!! password encode ediliyor
        updatedDean.setPassword(passwordEncoder.encode(deanRequest.getPassword()));
        Dean savedDean = deanRepository.save(updatedDean);

        return ResponseMessage.<DeanResponse>builder()
                .message(SuccessMessages.DEAN_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(deanMapper.mapDeanToDeanResponse(savedDean))
                .build();

    }

    private Dean isDeanExist(Long deanId){
        return deanRepository.findById(deanId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, deanId)));
    }

    // Not: Delete() ******************************************************************
    public ResponseMessage<?> deleteDeanById(Long deanId) {

        isDeanExist(deanId);

        deanRepository.deleteById(deanId);

        return ResponseMessage.builder()
                .message(SuccessMessages.DEAN_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // Not :  getById() **************************************************************
    public ResponseMessage<DeanResponse> getDeanById(Long deanId) {
        // Dean dean = isDeanExist(deanId);
        return ResponseMessage.<DeanResponse>builder()
                .message(SuccessMessages.DEAN_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(deanMapper.mapDeanToDeanResponse(isDeanExist(deanId)))
                .build();

    }

    // Not :  getAll() ***************************************************************
    public List<DeanResponse> getAllDeans() {
        return deanRepository.findAll() // List<Dean>
                .stream()  // Stream<Dean>
                .map(deanMapper::mapDeanToDeanResponse) // Stream<DeanResponse>
                .collect(Collectors.toList());
    }

    // Not :  getAllDeansByPage() ****************************************************
    public Page<DeanResponse> getAllDeansByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return deanRepository.findAll(pageable).map(deanMapper::mapDeanToDeanResponse);

    }
}
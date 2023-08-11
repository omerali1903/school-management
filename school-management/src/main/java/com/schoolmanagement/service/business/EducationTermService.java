package com.schoolmanagement.service.business;

import com.schoolmanagement.entity.concretes.business.EducationTerm;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.EducationTermMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.EducationTermRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;

    // Not: Save() ******************************************************************
    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {

        validateEducationTermDates(educationTermRequest);
        EducationTerm savedEducationTerm =
                educationTermRepository.save(educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest));

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .object(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest){
        // !!! gunler ile ilgili kontrol
        validateEducationTermDatesForUpdate(educationTermRequest);

        // !!! term ile ilgili kontrol
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR);
        }
    }

    private void validateEducationTermDatesForUpdate(EducationTermRequest educationTermRequest){

        // registrationDate > startDate
        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        // end > start
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }

    // Not: getById() ******************************************************************
    public EducationTermResponse getEducationTermResponseById(Long id) {

        EducationTerm term = isEducationTermExist(id);

        return educationTermMapper.mapEducationTermToEducationTermResponse(term);


    }

    private EducationTerm isEducationTermExist(Long id){
        return educationTermRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id)));
    }

    // Not: getAll() *************************************************************************
    public List<EducationTermResponse> getAllEducationTerms() {

        return educationTermRepository.findAll() // List<EducationTerm>
                .stream() // Stream<EducationTerm>
                .map(educationTermMapper::mapEducationTermToEducationTermResponse) // Stream<EducationTermResponse>
                .collect(Collectors.toList());
    }

    // Not: getAllWithPage() *****************************************************************
    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        return educationTermRepository.findAll(pageable).map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }

    // Not: deleteById() *********************************************************************
    public ResponseMessage deleteEducationTermById(Long id) {

        isEducationTermExist(id);
        educationTermRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // Not: updateById() *********************************************************************
    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest) {
        // !!! id var mi ???
        isEducationTermExist(id);

        // !!! girilen tarihler dogru mu ??
        //validateEducationTermDatesForUpdate(educationTermRequest);
        validateEducationTermDates(educationTermRequest);

        EducationTerm educationTermUpdated =
                educationTermRepository.save(educationTermMapper.mapEducationTermRequestToUpdatedEducationTerm(id, educationTermRequest));

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermUpdated))
                .build();

    }

    // Not: LessonProgramService icin yazildi
    public EducationTerm getEducationTermById(Long id){
        return isEducationTermExist(id);

    }
}
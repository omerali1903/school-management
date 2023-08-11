package com.schoolmanagement.service.business;

import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.schoolmanagement.entity.concretes.user.Teacher;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.AdvisoryTeacherMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.response.AdvisoryTeacherResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.AdvisoryTeacherRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import com.schoolmanagement.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisoryTeacherService {

    private final AdvisoryTeacherMapper advisoryTeacherMapper;
    private final PageableHelper pageableHelper;
    private final AdvisoryTeacherRepository advisoryTeacherRepository;
    private final UserRoleService userRoleService;

    // Not : getAll() ****************************************************************
    public List<AdvisoryTeacherResponse> getAll() {

        return  advisoryTeacherRepository.findAll()
                .stream()
                .map(advisoryTeacherMapper::mapAdvisorTeacherToAdvisorTeacherResponse)
                .collect(Collectors.toList());
    }

    // Not: getAllWithPage ***********************************************************
    public Page<AdvisoryTeacherResponse> getAllAdvisorTeacherByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return advisoryTeacherRepository.findAll(pageable)
                .map(advisoryTeacherMapper::mapAdvisorTeacherToAdvisorTeacherResponse);
    }

    // Not : Delete() ****************************************************************
    public ResponseMessage deleteAdvisorTeacherById(Long id) {
        AdvisoryTeacher advisoryTeacher = getAdvisoryTeacherById(id);
        advisoryTeacherRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public AdvisoryTeacher getAdvisoryTeacherById(Long advisoryTeacherId){
        return advisoryTeacherRepository.findById(advisoryTeacherId)
                .orElseThrow(()->
                        new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE, advisoryTeacherId)));
    }

    // Not: Save() --> TeacherService icin yazildi************************************8
    public void saveAdvisoryTeacher(Teacher teacher) {
        AdvisoryTeacher advisoryTeacher =  advisoryTeacherMapper.mapTeacherToAdvisoryTeacher(teacher);
        advisoryTeacher.setUserRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));

        advisoryTeacherRepository.save(advisoryTeacher);

    }
    // Not: update() --> TeacherService icin yazildi*************************************
    public void updateAdvisoryTeacher(boolean status, Teacher teacher) {

        Optional<AdvisoryTeacher> advisoryTeacher = advisoryTeacherRepository.getAdvisoryTeacherByTeacher_Id(teacher.getId());

        AdvisoryTeacher.AdvisoryTeacherBuilder advisoryTeacherBuilder = AdvisoryTeacher.builder()
                .teacher(teacher)
                .userRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));

        if(advisoryTeacher.isPresent()){
            if(status){
                advisoryTeacherBuilder.id(advisoryTeacher.get().getId());
                advisoryTeacherRepository.save(advisoryTeacherBuilder.build());
            } else {
                advisoryTeacherRepository.deleteById(advisoryTeacher.get().getId());
            }
        } else {
            // TODO : ici bos ve status degeri false olma durumu kontrolu eklenecek....
            advisoryTeacherRepository.save(advisoryTeacherBuilder.build());
        }
    }

    // Not: MeetService icin yazildi************************************************
    public AdvisoryTeacher getAdvisorTeacherByUsername(String username) {

        return advisoryTeacherRepository
                .findByTeacher_UsernameEquals(username)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.ADVISORY_TEACHER_NOT_FOUND_BY_USERNAME, username)));
    }
}
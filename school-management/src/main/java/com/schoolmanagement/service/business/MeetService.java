package com.schoolmanagement.service.business;

import com.schoolmanagement.entity.concretes.business.Meet;
import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.MeetMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.MeetRequest;
import com.schoolmanagement.payload.response.MeetResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.MeetRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import com.schoolmanagement.service.user.StudentService;
import com.schoolmanagement.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final DateTimeValidator dateTimeValidator;
    private final StudentService studentService;
    private final MeetMapper meetMapper;
    private final PageableHelper pageableHelper;

    // Not :  Save() **********************************************************
    public ResponseMessage<MeetResponse> saveMeet(HttpServletRequest httpServletRequest, MeetRequest meetRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(username);
        // !!! TODO : Teacher icin cakisma kontrolu gerekiyor
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(),meetRequest.getStopTime());

        for (Long studentId : meetRequest.getStudentIds()) {
            studentService.isStudentExist(studentId);
            checkMeetConflict(studentId,meetRequest.getDate(),meetRequest.getStartTime(),meetRequest.getStopTime());
        }

        // !!! Meet e katilacak ogrenciler getiriliyor
        List<Student> students = studentService.getStudentsById(meetRequest.getStudentIds());
        // !!! DTO --> POJO
        Meet meet = meetMapper.mapMeetRequestToMeet(meetRequest);
        meet.setStudentList(students);
        meet.setAdvisoryTeacher(advisoryTeacher);

        Meet savedMeet = meetRepository.save(meet);

        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_SAVE)
                .object(meetMapper.mapMeetToMeetResponse(savedMeet))
                .httpStatus(HttpStatus.OK)
                .build();


    }

    private void checkMeetConflict(Long studentId, LocalDate date, LocalTime startTime, LocalTime stopTime){

        // !!! Student a ait olan mevcut meetleri getiriyoruz
        List<Meet> meets = meetRepository.findByStudentList_IdEquals(studentId);

        // !!! cakisma kontrolu
        for (Meet meet: meets) {

            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if(meet.getDate().equals(date) &&
                    ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                            (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                            (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                            (startTime.equals(existingStartTime) && stopTime.equals(existingStopTime)))
            ){
                throw  new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
            }

        }
    }

    // Not: getAll *************************************************************
    public List<MeetResponse> getAll() {

        return meetRepository.findAll()
                .stream()
                .map(meetMapper::mapMeetToMeetResponse)
                .collect(Collectors.toList());
    }
    // Not: getByMeetId ********************************************************
    public ResponseMessage<MeetResponse> getMeetById(Long meetId) {
        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(meetMapper.mapMeetToMeetResponse(isMeetExistById(meetId)))
                .build();
    }

    private Meet isMeetExistById(Long meetId){
        return meetRepository.findById(meetId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.MEET_NOT_FOUND, meetId)));
    }

    // Not : Delete() ************************************************************
    public ResponseMessage delete(Long meetId) {
        isMeetExistById(meetId);
        meetRepository.deleteById(meetId);

        return ResponseMessage.builder()
                .message(SuccessMessages.MEET_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    // Not: Update() *************************************************************
    public ResponseMessage<MeetResponse> updateMeet(MeetRequest meetRequest, Long meetId) {
        Meet meet =  isMeetExistById(meetId);
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        if( !(meet.getDate().equals(meetRequest.getDate()) &&
                meet.getStartTime().equals(meetRequest.getStartTime()) &&
                meet.getStopTime().equals(meetRequest.getStopTime()))){
            //!!! Student icin cakisma var mi
            for (Long studentId: meetRequest.getStudentIds()) {
                checkMeetConflict(studentId,meetRequest.getDate(),meetRequest.getStartTime(),meetRequest.getStopTime());
            }
        }

        // !!! Studentlar getiriliyor
        List<Student> students = studentService.getStudentsById(meetRequest.getStudentIds());
        // !!! DTO --> POJO
        Meet updatedMeet = meetMapper.mapMeetUpdateRequestToMeet(meetRequest, meetId);
        updatedMeet.setStudentList(students);
        updatedMeet.setAdvisoryTeacher(meet.getAdvisoryTeacher());

        Meet savedMeet =  meetRepository.save(updatedMeet);

        return ResponseMessage.<MeetResponse>builder()
                .message(SuccessMessages.MEET_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(meetMapper.mapMeetToMeetResponse(savedMeet))
                .build();

    }

    // Not: getAllByAdvTeacher() ************************************************
    public ResponseEntity<List<MeetResponse>> getAllMeetByAdvTeacher(HttpServletRequest httpServletRequest) {
        String userName = (String) httpServletRequest.getAttribute("username");
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(userName);
        List<MeetResponse> meetResponseList = meetRepository.getByAdvisoryTeacher_IdEquals(advisoryTeacher.getId())
                .stream()
                .map(meetMapper::mapMeetToMeetResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(meetResponseList);
    }
    // Not: getAllMeetByStudent() ************************************************
    public List<MeetResponse> getAllMeetByStudent(HttpServletRequest httpServletRequest) {
        String userName = (String) httpServletRequest.getAttribute("username");
        Student student = studentService.isStudentExistByUsername(userName);
        return  meetRepository.findByStudentList_IdEquals(student.getId())
                .stream()
                .map(meetMapper::mapMeetToMeetResponse)
                .collect(Collectors.toList());
    }

    // Not: getAllWithPage ***********************************************************
    public Page<MeetResponse> getAllMeetByPage(int page, int size) {
        Pageable pageable =  pageableHelper.getPageableWithProperties(page, size);
        return meetRepository.findAll(pageable).map(meetMapper::mapMeetToMeetResponse);
    }
    // Not: gettAllByAdvTeacherByPage() **********************************************
    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest, int page, int size) {
        String userName = (String) httpServletRequest.getAttribute("username");
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(userName);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);

        return ResponseEntity.ok(meetRepository.findByAdvisoryTeacher_IdEquals(advisoryTeacher.getId(), pageable)
                .map(meetMapper::mapMeetToMeetResponse));

    }
}
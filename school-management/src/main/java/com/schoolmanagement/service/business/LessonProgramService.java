package com.schoolmanagement.service.business;

import com.schoolmanagement.entity.concretes.business.EducationTerm;
import com.schoolmanagement.entity.concretes.business.Lesson;
import com.schoolmanagement.entity.concretes.business.LessonProgram;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.LessonProgramMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.LessonProgramRequest;
import com.schoolmanagement.payload.response.LessonProgramResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.LessonProgramRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import com.schoolmanagement.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private  final EducationTermService educationTermService;
    private final DateTimeValidator dateTimeValidator;
    private final LessonProgramMapper lessonProgramMapper;
    private final PageableHelper pageableHelper;

    // Not :  Save() *********************************************************
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {
        //!!! LessonProgramda olacak dersleri LessonService den getiriyorum
        Set<Lesson> lessons = lessonService.getLessonByLessonIdSet(lessonProgramRequest.getLessonIdList()); // 5,6,7
        // !!! EducationTerm
        EducationTerm educationTerm = educationTermService.getEducationTermById(lessonProgramRequest.getEducationTermId());
        // !!! yukarda gelen lessons in icinin bos olma kontrolu
        if(lessons.isEmpty()){
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_IN_LIST_MESSAGE);
        }
        // !!! zaman kontrolu
        dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());
        // !!!  DTO --> POJO
        LessonProgram lessonProgram = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(lessonProgramRequest,lessons,educationTerm);
        LessonProgram savedLessonProgram =  lessonProgramRepository.save(lessonProgram);

        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .build();

    }

    // Not : getAll() **********************************************************************
    public List<LessonProgramResponse> getAllLessonProgramByList() {

        return lessonProgramRepository.findAll() // List<LessonProgram>
                .stream() // Stream<LessonProgram>
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse) // Steam<LessonProgramResponse>
                .collect(Collectors.toList());
    }

    // Not : getById() *********************************************************************
    public LessonProgramResponse getLessonProgramById(Long id) {

        LessonProgram lessonProgram = isLessonProgramExistById(id);

        return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(lessonProgram);
    }

    private LessonProgram isLessonProgramExistById(Long id){

        return lessonProgramRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE, id)));
    }

    // Not : getAllLessonProgramUnassigned() ************************************************
    public List<LessonProgramResponse> getAllLessonProgramUnassigned() {

        return lessonProgramRepository.findByTeachers_IdNull() // List<LessonProgram>
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    // Not : getAllLessonProgramAssigned() **************************************************
    public List<LessonProgramResponse> getAllAssigned() {

        return lessonProgramRepository.findByTeachers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    // Not : Delete() ***********************************************************************
    public ResponseMessage deleteLessonProgramById(Long id) {
        isLessonProgramExistById(id);
        lessonProgramRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.LESSON_PROGRAM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    // Not :  getAllWithPage() ***************************************************************
    public Page<LessonProgramResponse> getAllLessonProgramByPage(int page, int size, String sort, String type) {

        Pageable pageable =  pageableHelper.getPageableWithProperties(page,size,sort,type);
        return lessonProgramRepository.findAll(pageable).map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse);
    }

    // Not : getLessonProgramByTeacher() *****************************************************
    public Set<LessonProgramResponse> getAllLessonProgramByTeacher(HttpServletRequest httpServletRequest) {
        // Request uzerinden login olan kullanicinin username bilgisini aliyorum
        String userName = (String) httpServletRequest.getAttribute("username");
        return lessonProgramRepository.getLessonProgramByTeacherUsername(userName) // Set<LessonProgram>
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse) // Stream<LessonProgramResponse>
                .collect(Collectors.toSet());

    }
    // Not :  getLessonProgramByStudent() *****************************************************
    public Set<LessonProgramResponse> getAllLessonProgramByStudent(HttpServletRequest httpServletRequest) {

        String userName = (String) httpServletRequest.getAttribute("username");

        return lessonProgramRepository.getLessonProgramByStudentsUsername(userName)
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
    }

    // Not: TeacherService de kullanildi
    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonIdSet){

        Set<LessonProgram> lessonPrograms = lessonProgramRepository.getLessonProgramByLessonProgramByIdList(lessonIdSet);

        if(lessonPrograms.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO);
        }

        return lessonPrograms;

        /* // Alternatif kod
                lessonIdSet.forEach(this::isLessonProgramExistById);
         */

    }
}

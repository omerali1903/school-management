package com.schoolmanagement.payload.mappers;

import com.schoolmanagement.entity.concretes.business.Meet;
import com.schoolmanagement.payload.request.MeetRequest;
import com.schoolmanagement.payload.response.MeetResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MeetMapper {

    // !!! DTO --> POJO
    public Meet mapMeetRequestToMeet(MeetRequest meetRequest){
        return Meet.builder()
                .date(meetRequest.getDate())
                .startTime(meetRequest.getStartTime())
                .stopTime(meetRequest.getStopTime())
                .description(meetRequest.getDescription())
                .build();
    }

    //!!! Update DTO --> POJO
    public Meet mapMeetUpdateRequestToMeet(MeetRequest meetRequest, Long meetId){
        return Meet.builder()
                .id(meetId)
                .date(meetRequest.getDate())
                .startTime(meetRequest.getStartTime())
                .stopTime(meetRequest.getStopTime())
                .description(meetRequest.getDescription())
                .build();

    }

    //!!! POJO --> DTO
    public MeetResponse mapMeetToMeetResponse(Meet meet){
        return MeetResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisoryTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherName(meet.getAdvisoryTeacher().getTeacher().getName())
                .teacherSsn(meet.getAdvisoryTeacher().getTeacher().getSsn())
                .students(meet.getStudentList())
                .build();
    }
}
package com.schoolmanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolmanagement.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgramRequest {

    @NotNull(message = "Please enter Day")
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message = "Please enter start time")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message = "Please enter stop time")
    private LocalTime stopTime;

    @NotNull(message = "Please enter lesson")
    @Size(min=1, message = "Lesson mustn't be empty ")
    private Set<Long> lessonIdList;

    @NotNull(message = "Please enter education term")
    private Long educationTermId;
}
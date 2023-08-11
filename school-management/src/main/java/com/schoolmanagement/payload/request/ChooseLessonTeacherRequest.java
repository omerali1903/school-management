package com.schoolmanagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChooseLessonTeacherRequest {

    @NotNull(message = "Please select Lesson")
    @Size(min = 1, message = "Lesson mustn't be empty")
    private Set<Long> lessonProgramId;

    @NotNull(message = "Please select Teacher")
    private Long teacherId;
}
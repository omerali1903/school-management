package com.schoolmanagement.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolmanagement.entity.enums.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EducationTermRequest {

    @NotNull
    private Term term;

    @NotNull(message = "Start date mustn't be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date mustn't be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Last registration date mustn't be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDate;
}

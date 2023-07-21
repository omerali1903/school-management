package com.schoolmanagement.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.entity.concretes.user.Teacher;
import com.schoolmanagement.entity.enums.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee;

    private Double midtermExam;

    private Double finalExam;

    private Double examAverage;

    private String infoNote;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Note letterGrade; // AA,BA gibi

    @OneToOne  // TODO ManyToOne ???
    private EducationTerm educationTerm;

    @ManyToOne
    @JsonIgnoreProperties("lesson")
    private Lesson lesson;
}
package com.schoolmanagement.entity.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolmanagement.entity.concretes.user.UserRole;
import com.schoolmanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String ssn;

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // hassas veri oldugu icin okuma islemlerinde kullanilmasin

    @Column(unique = true)
    private String phoneNumber;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
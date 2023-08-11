package com.schoolmanagement.payload.messages;

public class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already send a message with thi e-mail";

    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: User with ssn %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: User with Phone Number %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "Error: There is no role like that, check the database";

    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "Error: You don't have any permission to do this operation";

    public static final String ROLE_ALREADY_EXIST = "Error: Role already exist in DB";


    public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date";
    public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date";
    public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR = "Error: Education Term with Term and Year already exist ";
    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education term not found with id %s";

    public static final String LESSON_ALREADY_EXIST = "Error: Lesson is exist with Lesson Name %s";
    public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson not found with id %s";
    public static final String NOT_FOUND_LESSON_IN_LIST_MESSAGE = "Error: Lesson not found in the list";

    public static final String TIME_NOT_VALID_MESSAGE = "Error: incorrect time";

    public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE = "Error: Lesson Program not found with id %s";
    public static final String LESSON_PROGRAM_ALREADY_EXIST = "Error: Course schedule can't be selected for the same hour and date";
    public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO = "Error: Lesson Program not found with this field";


    public static final String NOT_FOUND_ADVISOR_MESSAGE = "Error: Advisor Teacher not found with id %s";
    public static final String ADVISORY_TEACHER_NOT_FOUND_BY_USERNAME = "Error: Advisory Teacher not found with username %s ";

    public static final String STUDENT_INFO_NOT_FOUND = "Error: Student info with id %d not found";
    public static final String STUDENT_INFO_NOT_FOUND_BY_STUDENT_ID = "Error: Student info with student id %d not found";

    public static final String MEET_HOURS_CONFLICT = "Error: Meet hours has conflict with existing meets";
    public static final String MEET_NOT_FOUND = "Error: Meet with id %d not found";
}
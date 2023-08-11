package com.schoolmanagement;

import com.schoolmanagement.entity.enums.Gender;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.request.AdminRequest;
import com.schoolmanagement.service.user.AdminService;
import com.schoolmanagement.service.user.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final AdminService adminService;

	public SchoolManagementApplication(UserRoleService userRoleService, AdminService adminService) {
		this.userRoleService = userRoleService;
		this.adminService = adminService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SchoolManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// !!! Role tablosunu dolduralim
		if(userRoleService.getAllUserRole().isEmpty()) {
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.MANAGER);
			userRoleService.save(RoleType.ASSISTANT_MANAGER);
			userRoleService.save(RoleType.TEACHER);
			userRoleService.save(RoleType.ADVISORY_TEACHER);
			userRoleService.save(RoleType.STUDENT);
			userRoleService.save(RoleType.GUEST_USER);
		}

		// !!! BuiltIN Admin olusturuyoruz
		if(adminService.countAllAdmins()==0) {
			AdminRequest adminRequest = new AdminRequest();
			adminRequest.setUsername("Admin");
			adminRequest.setSsn("111-11-1111");
			adminRequest.setPassword("12345678");
			adminRequest.setName("Ahmet");
			adminRequest.setSurname("soyad");
			adminRequest.setPhoneNumber("111-111-1111");
			adminRequest.setGender(Gender.MALE);
			adminRequest.setBirthDay(LocalDate.of(1980,2,2));
			adminRequest.setBirthPlace("Texas");
			adminService.saveAdmin(adminRequest);
		}
	}
}
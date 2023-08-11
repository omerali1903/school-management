package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.concretes.user.Admin;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.AdminMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.AdminRequest;
import com.schoolmanagement.payload.response.AdminResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.user.AdminRepository;
import com.schoolmanagement.service.helper.PageableHelper;
import com.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final AdminMapper adminMapper;
    private final UserRoleService userRoleService;
    private final PageableHelper pageableHelper;
    private final PasswordEncoder passwordEncoder;

    // Not : save() *************************************************************
    public ResponseMessage<AdminResponse> saveAdmin(AdminRequest adminRequest) {

        // !!! Girilen username-ssn-phoneNumber unique mi kontrolu
        uniquePropertyValidator.checkDuplicate(adminRequest.getUsername(), adminRequest.getSsn(),
                adminRequest.getPhoneNumber());

        // !!! Dto -> POJO
        Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);

        if(Objects.equals(adminRequest.getUsername(), "Admin")) {
            admin.setBuilt_in(true);
        }

        // !!! admin rolu veriliyor
        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        System.out.println(admin.getPassword());
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // adminRequest.getPassword()
        System.out.println(admin.getPassword());

        Admin savedAdmin = adminRepository.save(admin);

        // ResponseMessage nesnesi olusturuluyor
        return ResponseMessage.<AdminResponse>builder()
                .message(SuccessMessages.ADMIN_CREATE)
                .object(adminMapper.mapAdminToAdminResponse(savedAdmin))
                .build();

    }

    // Not : getAll() **********************************************************
    public Page<Admin> getAllAdminsByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);

        return adminRepository.findAll(pageable);

    }

    // Not : delete() **********************************************************
    public String deleteAdminById(Long id) {
        // !!! id kontrolu
        Optional<Admin> admin = adminRepository.findById(id);

        if(admin.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,id));
        } else if (admin.get().isBuilt_in()) {
            throw new ConflictException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        adminRepository.deleteById(id);
        return SuccessMessages.ADMIN_DELETE;

    }

    // Not: Runner tarafi icin yazildi
    public long countAllAdmins() {
        return adminRepository.count();
    }

}
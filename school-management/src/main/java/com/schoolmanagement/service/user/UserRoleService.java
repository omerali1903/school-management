package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.concretes.user.UserRole;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){

        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(() ->
                new ConflictException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRole(){
        return userRoleRepository.findAll();
    }

    public UserRole save(RoleType roleType){
        if(userRoleRepository.existsByEnumRoleEquals(roleType)) {
            throw new ConflictException(ErrorMessages.ROLE_ALREADY_EXIST);
        }

        UserRole userRole = UserRole.builder().roleType(roleType).build();
        return userRoleRepository.save(userRole);
    }
}
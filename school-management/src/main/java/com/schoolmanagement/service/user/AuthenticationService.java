package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.request.LoginRequest;
import com.schoolmanagement.payload.response.AuthResponse;
import com.schoolmanagement.security.jwt.JwtUtils;
import com.schoolmanagement.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {

        //!!! request icinden username ve password aliniyor
        String username =loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // !!! AuthenticationManager uzerinden kullaniciyi valide ediyoruz
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        // !!! Valide edilen user context e gonderiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // !!! JWT token olusturuluyor
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication); // Bearer jhkjsdhfi76w4wghfdsuigfd7wgh

        // !!! response icindeki fieldlar dolduruluyor
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles  = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        // !!! Response nesnesi olusturuluyor
        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.username(userDetails.getUsername());
        authResponse.token(token.substring(7));
        authResponse.name(userDetails.getName());
        // !!! role bilgisi setleniyor
        if(role.isPresent()){
            authResponse.role(role.get());
            if(role.get().equalsIgnoreCase(RoleType.TEACHER.name())) {
                authResponse.isAdvisory(userDetails.getIsAdvisor().toString());
            }
        }

        return ResponseEntity.ok(authResponse.build());


    }
}
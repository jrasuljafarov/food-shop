package az.freelance.work.foodshop.service;

import az.freelance.work.foodshop.dto.JwtAuthenticationResponse;
import az.freelance.work.foodshop.dto.LoginRequest;
import az.freelance.work.foodshop.dto.SignUpRequest;
import az.freelance.work.foodshop.exception.AppException;
import az.freelance.work.foodshop.exception.ConflictException;
import az.freelance.work.foodshop.model.Role;
import az.freelance.work.foodshop.model.RoleName;
import az.freelance.work.foodshop.model.User;
import az.freelance.work.foodshop.repository.RoleRepository;
import az.freelance.work.foodshop.repository.UserRepository;
import az.freelance.work.foodshop.security.JwtTokenProvider;
import az.freelance.work.foodshop.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumber(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [phoneNumber: {}] has logged in", userPrincipal.getPhoneNumber());

        return new JwtAuthenticationResponse(jwt);
    }

    public Long registerUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new ConflictException(
                    "PhoneNumber [phoneNumber: " + signUpRequest.getPhoneNumber() + "] is already taken");
        }

        User user = new User(
                signUpRequest.getFullName(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getAddress(),
                signUpRequest.getPassword(),
                signUpRequest.getDeviceId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));

        user.setRoles(Collections.singleton(userRole));

        log.info("Successfully registered user with [phoneNumber: {}]", user.getPhoneNumber());

        return userRepository.save(user).getId();
    }

}

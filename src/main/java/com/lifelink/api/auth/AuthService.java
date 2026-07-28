package com.lifelink.api.auth;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lifelink.api.auth.login.LoginRequest;
import com.lifelink.api.auth.login.LoginResponse;
import com.lifelink.api.auth.signup.SignupResponse;
import com.lifelink.api.auth.signup.SignupRequest;
import com.lifelink.api.exception.ApiException;
import com.lifelink.api.exception.ErrorCode;
import com.lifelink.api.security.JwtService;
import com.lifelink.api.user.User;
import com.lifelink.api.user.UserRepository;
import com.lifelink.api.user.UserResponse;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    
    public SignupResponse signup(SignupRequest signupRequest) {
        User user = new User();
        // check if the email already exists in the database
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS, "Email already exists");
        } else {
            user.setEmail(signupRequest.getEmail());
            // encode the password before saving it to the database
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userRepository.save(user);
            // TODO: create login token and return it in the response
        }
        return new SignupResponse("User signed up successfully!");
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CREDENTIALS, "Invalid email or password."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS, "Invalid email or password.");
        }
        String token = jwtService.generateToken(user.getId());
        System.out.println("Generated token: " + token);
        return new LoginResponse(token, new UserResponse(user.getId(), user.getUsername()));
    }
}

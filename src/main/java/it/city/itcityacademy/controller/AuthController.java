package it.city.itcityacademy.controller;

import it.city.itcityacademy.entity.User;
import it.city.itcityacademy.payload.ApiResponse;
import it.city.itcityacademy.payload.JwtToken;
import it.city.itcityacademy.payload.ReqLogin;
import it.city.itcityacademy.payload.ReqUser;
import it.city.itcityacademy.repository.UserRepository;
import it.city.itcityacademy.security.JwtTokenProvider;
import it.city.itcityacademy.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    final AuthenticationManager authenticationManager;
    final JwtTokenProvider jwtTokenProvider;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/get")
    public HttpEntity<?> getUsers() {
        return ResponseEntity.ok().body(authService.getUserList());
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody ReqUser reqUser) {
        ApiResponse apiResponse = authService.register(reqUser);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> updateUser(@PathVariable UUID id, @RequestBody ReqUser reqUser) {
        ApiResponse apiResponse = authService.updateUser(id, reqUser);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse apiResponse = authService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }

    @GetMapping("/page")
    public HttpEntity<?> getStudentPage(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(authService.getUserPage(page, size));
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqLogin reqLogIn) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqLogIn.getUsername(), reqLogIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(new JwtToken(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse("Username or password error", false));
        }
    }
}

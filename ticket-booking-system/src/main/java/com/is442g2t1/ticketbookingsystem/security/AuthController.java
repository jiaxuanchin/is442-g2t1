package com.is442g2t1.ticketbookingsystem.security;

import com.is442g2t1.ticketbookingsystem.User.Role;
import com.is442g2t1.ticketbookingsystem.User.RoleRepository;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import com.is442g2t1.ticketbookingsystem.User.UserRepository;
import com.is442g2t1.ticketbookingsystem.security.DTO.AuthResponseDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.security.DTO.RegisterDTO;
import com.is442g2t1.ticketbookingsystem.security.jwt.JWTGenerator;
import com.is442g2t1.ticketbookingsystem.security.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    // -------------------- TESTER METHODS, TO BE REMOVED --------------------
    @GetMapping("/all1")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('customer', 'event_manager')")
    public String userAccess() {
        return "User Content.";
    }
    // -----------------------------------------------------------------------

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest){
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication)
        try {
            
            // ----------------------------- CHECKPOINT -----------------------------
            UsernamePasswordAuthenticationToken test = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());
            System.out.println("[CHECKPOINT AuthController] PRINT USER AUTH DETAILS: " + test);
            // -----------------------------------------------------------------------

            Authentication authentication = authenticationManager.authenticate( // trying to authenticate the provided Authentication object 
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication); 
            String token = jwtGenerator.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
            .findFirst()
            .map(item -> item.getAuthority())
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            // return new ResponseEntity.ok(new AuthResponseDTO(token), HttpStatus.OK);
            return ResponseEntity.ok(new AuthResponseDTO(token, 
                            userDetails.getId(), 
                            userDetails.getUsername(), 
                            userDetails.getEmail(), 
                            role));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        // Fetch the Role entity from the database based on the role name
        Role role = roleRepository.findByName("customer").orElse(null);

        if (role == null) {
            return new ResponseEntity<>("Role not found!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity(role, registerDto.getUser_fname(),registerDto.getUser_lname(),registerDto.getEmail(), 
            passwordEncoder.encode(registerDto.getPassword())
        );
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

}

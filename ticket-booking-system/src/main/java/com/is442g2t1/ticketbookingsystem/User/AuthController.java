package com.is442g2t1.ticketbookingsystem.User;

import com.is442g2t1.ticketbookingsystem.User.DTO.AuthResponseDTO;
import com.is442g2t1.ticketbookingsystem.User.DTO.LoginDTO;
import com.is442g2t1.ticketbookingsystem.User.DTO.RegisterDto;
import com.is442g2t1.ticketbookingsystem.security.JWTGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder; // Todo: expand on this
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder; // Todo: expand on this
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication)
        Authentication authentication = authenticationManager.authenticate( // trying to authenticate the provided Authentication object 
                new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication); 
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        // Fetch the Role entity from the database based on the role name
        Role role = roleRepository.findByName("customer").orElse(null);

        if (role == null) {
            return new ResponseEntity<>("Role not found!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity(role,registerDto.getUser_fname(),registerDto.getUser_lname(),registerDto.getEmail(),registerDto.getPassword());
        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}

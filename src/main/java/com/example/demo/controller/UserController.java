package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.CreateUserDto;
import com.example.demo.model.User;
import com.example.demo.security.AuthRequest;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserInfoDetails;
import com.example.demo.services.UserService;

@RestController
public class UserController {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserRepository userRepository, JdbcTemplate jdbcTemplate,
            AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;

    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDto createUserDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = new User(createUserDto);
           ;
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            // userRepository.save(user);

            response.put("message",  userService.addUser(user));
            response.put("user", user);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataIntegrityViolationException e) {
            response.put("message", "User Already Exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.put("message", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        userRepository.deleteById(id);
        response.put("message", "User Deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @RequestBody CreateUserDto updateUserDto) {
        Map<String, Object> response = new HashMap<>();
        try {

            User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println(existingUser.getName());
            existingUser.setName(updateUserDto.getName());
            existingUser.setEmail(updateUserDto.getEmail());
            existingUser.setAge(updateUserDto.getAge());
            userRepository.save(existingUser);
            response.put("message", "User profile Updated");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            System.out.println(HttpStatus.OK);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @PostMapping("/users/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (auth.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

    }

    @GetMapping("/city/{code}")
    public String getDistrictsByCountryCode(@PathVariable String code) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String name = ((UserInfoDetails) userDetails).getName();  // Now you can access getName()

                System.out.println(name);

        String sql = "SELECT Name FROM city WHERE CountryCode LIKE ?";
        String countrySql = "SELECT Name FROM country WHERE code = ?";

        try {
            String country = jdbcTemplate.queryForObject(countrySql, String.class, code);

            List<String> cities = jdbcTemplate.queryForList(sql, String.class, "%" + code + "%");

            return "<h2> The cities of " + country + " </h2> <h3>" + String.join(", ", cities) + "</h3>";
        } catch (EmptyResultDataAccessException e) {
            return "<h2> Country with code " + code + " not found. </h2> " +
                    "<button onclick=\"sessionStorage.setItem('hell','sss')\">Click Me</button>";
        }
    }
}
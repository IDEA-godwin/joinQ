package com.example.joinq.controller;

import com.example.joinq.domain.dto.UserDto;
import com.example.joinq.domain.entities.Organization;
import com.example.joinq.domain.entities.User;
import com.example.joinq.service.AuthService;
import com.example.joinq.service.UserAdministrationService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
//@CrossOrigin(/*{*/"*"/*, "http://localhost:4200/**"}*/)
public class UserAdministrationController {

    private final UserAdministrationService adminService;
    private final AuthService authService;

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok().body(adminService.registerUser(user));
    }

    @PostMapping("/register-organization")
    public ResponseEntity<Organization> registerOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok().body(adminService.createOrganizationAccount(organization));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok().body(authService.loginUser(body.get("username"), body.get("password")));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedInAccount() {
        return ResponseEntity.ok().body(adminService.getUserAccount());
    }
}

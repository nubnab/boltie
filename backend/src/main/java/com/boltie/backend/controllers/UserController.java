package com.boltie.backend.controllers;

import com.boltie.backend.dto.RoleChangeDto;
import com.boltie.backend.dto.UserRoleDto;
import com.boltie.backend.dto.UsernameDto;
import com.boltie.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRoleDto>> getUserRoles() {
        return ResponseEntity.ok(userService.fetchUserRoles());
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<UsernameDto>> getUsernames() {
        return ResponseEntity.ok(userService.getAllUsernames());
    }

    @PatchMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeUserRole(@PathVariable("id") Long id,
                                            @RequestBody RoleChangeDto role) {
        userService.changeUserRole(id, role.role());
        return ResponseEntity.ok().build();
    }

}

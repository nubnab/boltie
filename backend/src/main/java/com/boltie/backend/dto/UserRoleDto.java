package com.boltie.backend.dto;

import com.boltie.backend.enums.Role;

public record UserRoleDto (Long id, String username, Role role) {
}

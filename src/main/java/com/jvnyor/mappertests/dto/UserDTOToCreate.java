package com.jvnyor.mappertests.dto;

import java.util.List;

public record UserDTOToCreate(
    String name,
    String email,
    String password,
    List<String> roles
) {
}

package com.jvnyor.mappertests.dto;

import java.util.List;

public record UserDTOToUpdate(
    Long id,
    String name,
    String email,
    String password,
    List<String> roles
) {
}

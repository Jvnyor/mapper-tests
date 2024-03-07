package com.jvnyor.mappertests.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;
}

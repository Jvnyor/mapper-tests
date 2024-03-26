package com.jvnyor.mappertests.mapper;

import com.jvnyor.mappertests.dto.UserDTOToCreate;
import com.jvnyor.mappertests.dto.UserDTOToUpdate;
import com.jvnyor.mappertests.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserDTOToCreate userDTOsToCreate;
    private List<UserDTOToUpdate> userDTOsToUpdate;

    @BeforeEach
    void setUp() {
        this.userDTOsToCreate = new UserDTOToCreate(
                "John Doe",
                "user@email.com",
                "123",
                Collections.singletonList("user")
        );

        this.userDTOsToUpdate = List.of(
                new UserDTOToUpdate(
                        1L,
                        "John Doe",
                        "user1@email.com",
                        "123",
                        List.of("user", "admin")
                ),
                new UserDTOToUpdate(
                        2L,
                        "John Doe",
                        "user2@email.com",
                        "123",
                        List.of("user")
                )
        );
    }

    @Test
    void givenUserDTO_whenCreateUserFromDTO_thenReturnUser() {
        var userEntity = UserMapper.createUserFromDTO(userDTOsToCreate);
        assertAll(
                () -> assertNull(userEntity.getId()),
                () -> assertEquals(userDTOsToCreate.name(), userEntity.getName()),
                () -> assertEquals(userDTOsToCreate.email(), userEntity.getEmail()),
                () -> assertEquals(userDTOsToCreate.password(), userEntity.getPassword()),
                () -> assertEquals(userDTOsToCreate.roles(), userEntity.getRoles())
        );
    }

    @Test
    void givenExistingUserAndUserDTO_whenUpdateExistingUserFromDTO_thenUpdateUser() {
        var existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Jane Doe");
        existingUser.setEmail("");
        existingUser.setPassword("");
        existingUser.setRoles(Collections.emptyList());
        var userDTOToUpdate = userDTOsToUpdate.get(0);
        var userUpdated = UserMapper.updateExistingUserFromDTO(userDTOToUpdate, existingUser);
        assertAll(
                () -> assertEquals(userDTOToUpdate.id(), userUpdated.getId()),
                () -> assertEquals(userDTOToUpdate.name(), userUpdated.getName()),
                () -> assertEquals(userDTOToUpdate.email(), userUpdated.getEmail()),
                () -> assertEquals(userDTOToUpdate.password(), userUpdated.getPassword()),
                () -> assertEquals(userDTOToUpdate.roles(), userUpdated.getRoles())
        );
    }

    @Test
    void givenUserDTOs_whenCreateUsersFromDTOs_thenReturnUsers() {
        var userEntities = UserMapper.createUsersFromDTOs(List.of(userDTOsToCreate));
        var userEntity = userEntities.get(0);
        assertAll(
                () -> assertEquals(1, userEntities.size()),
                () -> assertNull(userEntity.getId()),
                () -> assertEquals(userDTOsToCreate.name(), userEntity.getName()),
                () -> assertEquals(userDTOsToCreate.email(), userEntity.getEmail()),
                () -> assertEquals(userDTOsToCreate.password(), userEntity.getPassword()),
                () -> assertEquals(userDTOsToCreate.roles(), userEntity.getRoles())
        );
    }

    @Test
    void givenUserDTOsToUpdateAndExistingUsers_whenUpdateExistingUsersFromDTOsWithMap_thenReturnUpdateUsers() {
        var existingUsers = List.of(
                new User(),
                new User()
        );
        existingUsers.get(0).setId(1L);
        existingUsers.get(1).setId(2L);
        var usersUpdated = UserMapper.updateExistingUsersFromDTOsWithMap(userDTOsToUpdate, existingUsers);
        assertAll(
                () -> assertEquals(userDTOsToUpdate.get(0).name(), usersUpdated.get(0).getName()),
                () -> assertEquals(userDTOsToUpdate.get(0).email(), usersUpdated.get(0).getEmail()),
                () -> assertEquals(userDTOsToUpdate.get(0).password(), usersUpdated.get(0).getPassword()),
                () -> assertEquals(userDTOsToUpdate.get(0).roles(), usersUpdated.get(0).getRoles()),
                () -> assertEquals(userDTOsToUpdate.get(1).name(), usersUpdated.get(1).getName()),
                () -> assertEquals(userDTOsToUpdate.get(1).email(), usersUpdated.get(1).getEmail()),
                () -> assertEquals(userDTOsToUpdate.get(1).password(), usersUpdated.get(1).getPassword()),
                () -> assertEquals(userDTOsToUpdate.get(1).roles(), usersUpdated.get(1).getRoles())
        );
    }

    @Test
    void givenUserDTOsToUpdateAndExistingUsers_whenUpdateExistingUsersFromDTOsWithFor_thenReturnUpdateUsers() {
        var existingUsers = List.of(
                new User(),
                new User()
        );
        existingUsers.get(0).setId(1L);
        existingUsers.get(1).setId(2L);
        var usersUpdated = UserMapper.updateExistingUsersFromDTOsWithFor(userDTOsToUpdate, existingUsers);
        assertAll(
                () -> assertEquals(userDTOsToUpdate.get(0).name(), usersUpdated.get(0).getName()),
                () -> assertEquals(userDTOsToUpdate.get(0).email(), usersUpdated.get(0).getEmail()),
                () -> assertEquals(userDTOsToUpdate.get(0).password(), usersUpdated.get(0).getPassword()),
                () -> assertEquals(userDTOsToUpdate.get(0).roles(), usersUpdated.get(0).getRoles()),
                () -> assertEquals(userDTOsToUpdate.get(1).name(), usersUpdated.get(1).getName()),
                () -> assertEquals(userDTOsToUpdate.get(1).email(), usersUpdated.get(1).getEmail()),
                () -> assertEquals(userDTOsToUpdate.get(1).password(), usersUpdated.get(1).getPassword()),
                () -> assertEquals(userDTOsToUpdate.get(1).roles(), usersUpdated.get(1).getRoles())
        );
    }
}
package com.jvnyor.mappertests.mapper;

import com.jvnyor.mappertests.dto.UserDTOToCreate;
import com.jvnyor.mappertests.dto.UserDTOToUpdate;
import com.jvnyor.mappertests.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User createUserFromDTO(UserDTOToCreate userDTOToCreate) {
        if (userDTOToCreate == null) {
            return null;
        }
        var user = new User();
        BeanUtils.copyProperties(userDTOToCreate, user);
        return user;
    }

    public static User updateExistingUserFromDTO(UserDTOToUpdate userDTOToUpdate, User existingUser) {
        if (userDTOToUpdate == null) {
            return null;
        }
        BeanUtils.copyProperties(userDTOToUpdate, existingUser);
        return existingUser;
    }

    public static List<User> createUsersFromDTOs(List<UserDTOToCreate> userDTOsToCreate) {
        if (userDTOsToCreate == null || userDTOsToCreate.isEmpty()) {
            return Collections.emptyList();
        }
        return userDTOsToCreate.stream()
                .map(UserMapper::createUserFromDTO)
                .toList();
    }

    public static List<User> updateExistingUsersFromDTOsWithMap(List<UserDTOToUpdate> userDTOsToUpdate, List<User> existingUsers) {
        if (userDTOsToUpdate == null || userDTOsToUpdate.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, UserDTOToUpdate> dtoMap = userDTOsToUpdate.stream()
                .collect(Collectors.toMap(UserDTOToUpdate::id, Function.identity()));

        return existingUsers.stream()
                .map(user -> {
                    var dto = dtoMap.get(user.getId());
                    if (dto != null) {
                        return updateExistingUserFromDTO(dto, user);
                    } else {
                        return user;
                    }
                })
                .toList();
    }

    public static List<User> updateExistingUsersFromDTOsWithFor(List<UserDTOToUpdate> userDTOsToUpdate, List<User> existingUsers) {
        if (userDTOsToUpdate == null || userDTOsToUpdate.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, User> userMap = existingUsers.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<User> updatedUsers = new ArrayList<>();
        for (UserDTOToUpdate dto : userDTOsToUpdate) {
            var user = userMap.get(dto.id());
            if (user != null) {
                updatedUsers.add(updateExistingUserFromDTO(dto, user));
            }
        }

        return updatedUsers;
    }
}

package com.jvnyor.mappertests.mapper;

import com.jvnyor.mappertests.dto.UserDTOToCreate;
import com.jvnyor.mappertests.dto.UserDTOToUpdate;
import com.jvnyor.mappertests.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
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

    public static void updateExistingUserFromDTO(UserDTOToUpdate userDTOToUpdate, User existingUser) {
        if (userDTOToUpdate == null || existingUser == null) {
            return;
        }
        BeanUtils.copyProperties(userDTOToUpdate, existingUser);
    }

    public static List<User> createUsersFromDTOs(List<UserDTOToCreate> userDTOToCreates) {
        return userDTOToCreates.stream()
                .map(UserMapper::createUserFromDTO)
                .toList();
    }

    public static void updateExistingUsersFromDTOsWithMap(List<UserDTOToUpdate> userDTOsToUpdate, List<User> existingUsers) {
        if (userDTOsToUpdate == null || existingUsers == null) {
            return;
        }
        long updatedUsersCount = existingUsers.stream()
                .flatMap(user -> userDTOsToUpdate.stream()
                        .filter(dto -> dto.id().equals(user.getId()))
                        .findFirst()
                        .map(dto -> {
                            updateExistingUserFromDTO(dto, user);
                            return user;
                        })
                        .stream())
                .count();
        log.info("Updated users size: {}", updatedUsersCount);
    }

    public static void updateExistingUsersFromDTOsWithFor(List<UserDTOToUpdate> userDTOsToUpdate, List<User> existingUsers) {
        if (userDTOsToUpdate == null || existingUsers == null) {
            return;
        }
        userDTOsToUpdate.forEach(userDTOToUpdate -> existingUsers.stream()
                .filter(u -> u.getId().equals(userDTOToUpdate.id()))
                .findFirst()
                .ifPresent(user -> updateExistingUserFromDTO(userDTOToUpdate, user)));
        log.info("Updated users size: {}", existingUsers.size());
    }
}

package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.auth.AuthenticatedUser;
import com.example.ordermanager.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Mapping(target = "role", source = "role.value")
    public abstract AuthenticatedUser userToAuthenticatedUser(User user);
}

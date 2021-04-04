package com.demo.records;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings(value = {
        @Mapping(source = "firstName", target = "name"),
        @Mapping(source = "lastName", target = "surname")
    })
    UserDto toDto(User user);
}

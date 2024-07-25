package my.project.vehiclefleetmanagement.config;

import my.project.vehiclefleetmanagement.model.dtos.user.SignUpDto;
import my.project.vehiclefleetmanagement.model.dtos.user.UserDto;
import my.project.vehiclefleetmanagement.model.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDto signUpDto);

}

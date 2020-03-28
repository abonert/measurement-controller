package software.jevera.measurementcontroller.service.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import software.jevera.measurementcontroller.domain.user.User;
import software.jevera.measurementcontroller.service.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Override
    @Mapping(target = "state", ignore = true)
    User toEntity(UserDTO dto);

    @Override
    UserDTO toDto(User entity);

    @Override
    List<User> toEntity(List<UserDTO> dtoList);

    @Override
    List<UserDTO> toDto(List<User> entityList);

    @Override
    @InheritConfiguration(name = "toEntity")
    void updateEntity(UserDTO source, @MappingTarget User target);
}

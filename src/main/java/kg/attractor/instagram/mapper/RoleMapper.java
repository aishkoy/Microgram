package kg.attractor.instagram.mapper;

import kg.attractor.instagram.dto.RoleDto;
import kg.attractor.instagram.enitity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto dto);
    RoleDto toDto(Role entity);
}

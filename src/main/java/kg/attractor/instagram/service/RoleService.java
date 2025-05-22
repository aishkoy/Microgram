package kg.attractor.instagram.service;

import kg.attractor.instagram.dto.RoleDto;

public interface RoleService {
    RoleDto getByName(String name);
}

package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.dto.RoleDto;
import kg.attractor.instagram.entity.Role;
import kg.attractor.instagram.mapper.RoleMapper;
import kg.attractor.instagram.repository.RoleRepository;
import kg.attractor.instagram.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role " + name + " not found"));
        log.info("Получена роль с именем " + role.getName());
        return roleMapper.toDto(role);
    }
}

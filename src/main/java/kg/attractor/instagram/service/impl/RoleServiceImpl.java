package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.entity.Role;
import kg.attractor.instagram.repository.RoleRepository;
import kg.attractor.instagram.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findAll().stream()
                .filter(role -> name.equalsIgnoreCase(role.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role " + name + " not found"));
    }
}

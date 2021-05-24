package com.joiner.main.services.implementations;

import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.models.Role;
import com.joiner.main.repositories.RoleRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ShowRoleService implements com.joiner.main.services.interfaces.ShowRoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public ShowRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @SneakyThrows
    @Override
    public List<Role> execute(Long[] ids) {
        log.info("Search role with ID");
        List<Role> roles = new ArrayList<>();
        Iterable<Role> iterable = this.roleRepository.findAllById(Arrays.asList(ids));
        iterable.forEach(roles::add);

        if(roles.size() != ids.length) {
            throw new RoleNotFoundException();
        }

        return roles;
    }
}

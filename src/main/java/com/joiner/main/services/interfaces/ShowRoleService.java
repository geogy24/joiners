package com.joiner.main.services.interfaces;

import com.joiner.main.models.Role;

import java.util.List;

public interface ShowRoleService {
    List<Role> execute(Long[] ids);
}

package com.joiner.main.controllers;

import com.joiner.main.models.Role;
import com.joiner.main.services.interfaces.ShowRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Entry point to roles routes
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/roles")
@Log4j2
public class RoleController {
    private final ShowRoleService showRoleService;

    @Autowired
    public RoleController(ShowRoleService showRoleService) {
        this.showRoleService = showRoleService;
    }

    @GetMapping
    @ApiOperation(value = "Return role searched")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stack searched"),
            @ApiResponse(code = 404, message = "Stack not found")
    })
    public List<Role> show(@RequestParam Long[] id) {
        log.info("Search a role with IDs");
        return this.showRoleService.execute(id);
    }
}

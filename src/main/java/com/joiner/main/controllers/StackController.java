package com.joiner.main.controllers;

import com.joiner.main.models.Stack;
import com.joiner.main.services.interfaces.ShowJoinerService;
import com.joiner.main.services.interfaces.ShowStackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point to stacks routes
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/stacks")
@Log4j2
public class StackController {
    private final ShowStackService showStackService;

    @Autowired
    public StackController(ShowStackService showStackService) {
        this.showStackService = showStackService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Return stack searched")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stack searched"),
            @ApiResponse(code = 404, message = "Stack not found")
    })
    public Stack show(@PathVariable Long id) {
        log.info("Search a stack with ID {}", id);
        return this.showStackService.execute(id);
    }
}

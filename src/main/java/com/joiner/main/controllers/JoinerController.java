package com.joiner.main.controllers;

import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.dtos.UpdateJoinerDto;
import com.joiner.main.models.Joiner;
import com.joiner.main.services.CreateJoinerServiceInterface;
import com.joiner.main.services.ShowJoinerServiceInterface;
import com.joiner.main.services.UpdateJoinerServiceInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Entry point to joiners routes
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/joiners")
@Log4j2
public class JoinerController {
    private final CreateJoinerServiceInterface createJoinerServiceInterface;
    private final ShowJoinerServiceInterface showJoinerServiceInterface;
    private final UpdateJoinerServiceInterface updateJoinerServiceInterface;

    @Autowired
    public JoinerController(
            CreateJoinerServiceInterface createJoinerServiceInterface,
            ShowJoinerServiceInterface showJoinerServiceInterface,
            UpdateJoinerServiceInterface updateJoinerServiceInterface) {
        this.createJoinerServiceInterface = createJoinerServiceInterface;
        this.showJoinerServiceInterface = showJoinerServiceInterface;
        this.updateJoinerServiceInterface = updateJoinerServiceInterface;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Return joiner created")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Joiner created")
    })
    public Joiner create(@Valid @RequestBody JoinerDto joinerDto) {
        log.info("Create a joiner with data {}", joinerDto);
        return this.createJoinerServiceInterface.execute(joinerDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Return joiner searched")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Joiner searched"),
            @ApiResponse(code = 404, message = "Joiner not found")
    })
    public Joiner show(@PathVariable Long id) {
        log.info("Search a joiner with ID {}", id);
        return this.showJoinerServiceInterface.execute(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Return joiner updated")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Joiner searched"),
            @ApiResponse(code = 404, message = "Joiner not found")
    })
    public Joiner update(@PathVariable Long id, @Valid @RequestBody UpdateJoinerDto updateJoinerDto) {
        log.info("Update a joiner with ID {} data {}", id, updateJoinerDto);
        return this.updateJoinerServiceInterface.execute(id, updateJoinerDto);
    }
}

package com.joiner.main.controllers;

import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.models.Joiner;
import com.joiner.main.services.CreateJoinerServiceInterface;
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

    @Autowired
    public JoinerController(CreateJoinerServiceInterface createJoinerServiceInterface) {
        this.createJoinerServiceInterface = createJoinerServiceInterface;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Return book searched")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Joiner created")
    })
    public Joiner create(@Valid @RequestBody JoinerDto joinerDto) {
        log.info("Create a joiner with data {}", joinerDto);
        return this.createJoinerServiceInterface.execute(joinerDto);
    }
}

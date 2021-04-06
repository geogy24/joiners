package com.joiner.main.services;

import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.exceptions.LanguageLevelNotFoundException;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.models.Joiner;
import com.joiner.main.models.LanguageLevel;
import com.joiner.main.models.Role;
import com.joiner.main.models.Stack;
import com.joiner.main.repositories.JoinerRepository;
import com.joiner.main.repositories.LanguageLevelRepository;
import com.joiner.main.repositories.RoleRepository;
import com.joiner.main.repositories.StackRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateJoinerService implements CreateJoinerServiceInterface {
    private final JoinerRepository joinerRepository;
    private final RoleRepository roleRepository;
    private final LanguageLevelRepository languageLevelRepository;
    private final StackRepository stackRepository;

    @Autowired
    public CreateJoinerService(
            JoinerRepository joinerRepository,
            RoleRepository roleRepository,
            LanguageLevelRepository languageLevelRepository,
            StackRepository stackRepository
    ) {
        this.joinerRepository = joinerRepository;
        this.roleRepository = roleRepository;
        this.languageLevelRepository = languageLevelRepository;
        this.stackRepository = stackRepository;
    }

    @Override
    public Joiner execute(JoinerDto joinerDto) {
        log.info("Create a joiner from service with data {}", joinerDto);
        Joiner joiner = this.buildJoiner(joinerDto);
        log.info("Save joiner model with data {}", joiner);
        return this.joinerRepository.save(joiner);
    }

    private Joiner buildJoiner(JoinerDto joinerDto) {
        log.info("Build joiner model with data {}", joinerDto);
        Joiner joiner = joinerDto.toJoiner();
        joiner.setRole(this.findRole(joinerDto.getRoleId()));
        joiner.setLanguageLevel(this.findLanguageLevel(joinerDto.getLanguageLevelId()));
        joiner.setStack(this.findStack(joinerDto.getStackId()));
        log.info("Joiner model built {}", joiner);
        return joiner;
    }

    @SneakyThrows
    private Role findRole(Long roleId) {
        log.info("Find role with Id {}", roleId);
        return this.roleRepository.findById(roleId)
                .orElseThrow(RoleNotFoundException::new);
    }

    @SneakyThrows
    private LanguageLevel findLanguageLevel(Long languageLevelId) {
        log.info("Find language level with Id {}", languageLevelId);
        return this.languageLevelRepository.findById(languageLevelId)
                .orElseThrow(LanguageLevelNotFoundException::new);
    }

    @SneakyThrows
    private Stack findStack(Long stackId) {
        log.info("Find stack with Id {}", stackId);
        return this.stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
    }
}

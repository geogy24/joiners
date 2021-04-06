package com.joiner.main.services;

import com.joiner.main.dtos.UpdateJoinerDto;
import com.joiner.main.exceptions.JoinerNotFoundException;
import com.joiner.main.exceptions.LanguageLevelNotFoundException;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.models.Joiner;
import com.joiner.main.repositories.JoinerRepository;
import com.joiner.main.repositories.LanguageLevelRepository;
import com.joiner.main.repositories.RoleRepository;
import com.joiner.main.repositories.StackRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class UpdateJoinerService implements UpdateJoinerServiceInterface {
    private final JoinerRepository joinerRepository;
    private final RoleRepository roleRepository;
    private final LanguageLevelRepository languageLevelRepository;
    private final StackRepository stackRepository;

    @Autowired
    public UpdateJoinerService(
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
    public Joiner execute(Long id, UpdateJoinerDto updateJoinerDto) {
        log.info("Update a joiner with ID {} data {}", id, updateJoinerDto);
        Joiner joiner = this.findJoiner(id);
        Joiner updateJoiner = this.makeJoinerChanges(joiner, updateJoinerDto);
        log.info("Update joiner model with data {}", updateJoiner);
        return this.joinerRepository.save(updateJoiner);
    }

    @SneakyThrows
    private Joiner findJoiner(Long id) {
        return this.joinerRepository.findById(id)
                .orElseThrow(JoinerNotFoundException::new);
    }

    private Joiner makeJoinerChanges(Joiner joiner, UpdateJoinerDto updateJoinerDto) {
        log.info("Build joiner model with data {}", updateJoinerDto);

        if(Objects.nonNull(updateJoinerDto.getRoleId())){
            this.existsRole(updateJoinerDto.getRoleId());
        }

        if(Objects.nonNull(updateJoinerDto.getLanguageLevelId())){
            this.existsLanguageLevel(updateJoinerDto.getLanguageLevelId());
        }

        if(Objects.nonNull(updateJoinerDto.getStackId())){
            this.existsStack(updateJoinerDto.getStackId());
        }

        Joiner updateJoiner = updateJoinerDto.toJoiner(joiner);
        log.info("Joiner model built {}", updateJoiner);

        return updateJoiner;
    }

    @SneakyThrows
    private void existsRole(Long roleId) {
        log.info("Exists role with Id {}", roleId);
        this.roleRepository.findById(roleId)
                .orElseThrow(RoleNotFoundException::new);
    }

    @SneakyThrows
    private void existsLanguageLevel(Long languageLevelId) {
        log.info("Exists language level with Id {}", languageLevelId);
        this.languageLevelRepository.findById(languageLevelId)
                .orElseThrow(LanguageLevelNotFoundException::new);
    }

    @SneakyThrows
    private void existsStack(Long stackId) {
        log.info("Exists stack with Id {}", stackId);
        this.stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
    }
}

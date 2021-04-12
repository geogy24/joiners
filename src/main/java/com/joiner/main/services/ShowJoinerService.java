package com.joiner.main.services;

import com.joiner.main.exceptions.JoinerNotFoundException;
import com.joiner.main.models.Joiner;
import com.joiner.main.repositories.JoinerRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShowJoinerService implements ShowJoinerServiceInterface {
    private final JoinerRepository joinerRepository;

    @Autowired
    public ShowJoinerService(JoinerRepository joinerRepository) {
        this.joinerRepository = joinerRepository;
    }

    @SneakyThrows
    @Override
    public Joiner execute(Long id) {
        log.info("Search joiner with ID {}", id);
        return this.joinerRepository.findById(id)
                .orElseThrow(JoinerNotFoundException::new);
    }
}

package com.joiner.main.services.implementations;

import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.models.Stack;
import com.joiner.main.repositories.StackRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShowStackService implements com.joiner.main.services.interfaces.ShowStackService {
    private final StackRepository stackRepository;

    @Autowired
    public ShowStackService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    @SneakyThrows
    @Override
    public Stack execute(Long id) {
        log.info("Search stack with ID {}", id);
        return this.stackRepository.findById(id)
                .orElseThrow(StackNotFoundException::new);
    }
}

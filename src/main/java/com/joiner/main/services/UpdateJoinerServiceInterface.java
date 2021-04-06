package com.joiner.main.services;

import com.joiner.main.dtos.UpdateJoinerDto;
import com.joiner.main.models.Joiner;

public interface UpdateJoinerServiceInterface {
    Joiner execute(Long id, UpdateJoinerDto updateJoinerDto);
}

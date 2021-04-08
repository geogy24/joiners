package com.joiner.main.services.interfaces;

import com.joiner.main.dtos.UpdateJoinerDto;
import com.joiner.main.models.Joiner;

public interface UpdateJoinerService {
    Joiner execute(Long id, UpdateJoinerDto updateJoinerDto);
}

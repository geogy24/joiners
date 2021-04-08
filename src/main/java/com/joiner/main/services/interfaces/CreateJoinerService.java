package com.joiner.main.services.interfaces;

import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.models.Joiner;

public interface CreateJoinerService {
    Joiner execute(JoinerDto joinerDto);
}

package com.joiner.main.services;

import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.models.Joiner;

public interface CreateJoinerServiceInterface {
    Joiner execute(JoinerDto joinerDto);
}

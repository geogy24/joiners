package com.joiner.main.repositories;

import com.joiner.main.models.Joiner;
import org.springframework.data.repository.CrudRepository;

public interface JoinerRepository extends CrudRepository<Joiner, Long> {
}

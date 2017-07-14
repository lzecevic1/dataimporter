package com.company.repository;

import com.company.enums.ConnectionType;
import com.company.model.ConnectionData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConnectionDataRepository extends CrudRepository<ConnectionData, Integer> {
    Optional<ConnectionData> findByType(ConnectionType type);
}

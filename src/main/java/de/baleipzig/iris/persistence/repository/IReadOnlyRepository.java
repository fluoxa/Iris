package de.baleipzig.iris.persistence.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

@NoRepositoryBean
public interface IReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
}

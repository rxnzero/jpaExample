package com.dhlee.jpa.custom;

import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long>, UserRepositoryCustom {
}

package com.hosnaml.store.repositories;

import com.hosnaml.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

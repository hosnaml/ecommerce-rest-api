package com.hosnaml.store.repositories;

import com.hosnaml.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
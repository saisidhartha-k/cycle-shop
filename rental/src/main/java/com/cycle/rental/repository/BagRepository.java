package com.cycle.rental.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cycle.rental.entity.Bag;
import com.cycle.rental.entity.User;

public interface BagRepository extends CrudRepository<Bag,Integer>{

    Optional<Bag> findByUser(User user);
    
}

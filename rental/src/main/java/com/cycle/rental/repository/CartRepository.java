package com.cycle.rental.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cycle.rental.entity.Cart;
import com.cycle.rental.entity.User;

public interface CartRepository extends CrudRepository<Cart,Integer>{
    Optional<Cart> findByUser(User user);
        
}

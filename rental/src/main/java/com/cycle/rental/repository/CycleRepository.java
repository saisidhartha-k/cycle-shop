package com.cycle.rental.repository;


import com.cycle.rental.entity.Cycle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleRepository extends CrudRepository<Cycle, Integer> {
}

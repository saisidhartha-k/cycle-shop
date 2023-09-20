package com.cycle.rental.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycle.rental.entity.Bag;
import com.cycle.rental.repository.BagRepository;

@Service
public class BagService {
    
    @Autowired
    private BagRepository bagRepository;

    public List<Bag> getAllBags(){
        List<Bag> bags = new ArrayList<>();
        bagRepository.findAll().forEach(bag -> bags.add(bag));
        return bags;
    }
}

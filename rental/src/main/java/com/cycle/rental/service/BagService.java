package com.cycle.rental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cycle.rental.entity.Bag;
import com.cycle.rental.entity.Cycle;
import com.cycle.rental.entity.Items;
import com.cycle.rental.repository.BagRepository;
import com.cycle.rental.repository.CycleRepository;
import com.cycle.rental.repository.UserRepository;

@Service
public class BagService {
    
    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Bag> getAllBags(){
        List<Bag> bags = new ArrayList<>();
        bagRepository.findAll().forEach(bag -> bags.add(bag));
        return bags;
    }

    public String returnFromBag(String name){
        Optional<Bag> bag = bagRepository.findByUser(userRepository.findByName(name));
        if(bag.isPresent()){
            List<Items> items =bag.get().getItems();
            for(Items i: items){
                Cycle cycle = cycleRepository.findById(i.getCycle().getCycleId()).get();
                cycle.setNumBorrowed(0);
                cycleRepository.save(cycle);
            }
            bagRepository.delete(bag.get());
            return "Returned Successfully";
        }
        else{
            return "Bag Empty";
        }
    }
}

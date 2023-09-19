package com.cycle.rental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cycle.rental.entity.Bag;
import com.cycle.rental.entity.Cart;
import com.cycle.rental.entity.Cycle;
import com.cycle.rental.entity.Items;
import com.cycle.rental.entity.User;
import com.cycle.rental.repository.BagRepository;
import com.cycle.rental.repository.CartRepository;
import com.cycle.rental.repository.CycleRepository;
import com.cycle.rental.repository.UserRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired BagRepository bagRepository;

    public List<Items> addToCart(int cycleId,int quantity,String name){
        User user = userRepository.findByName(name).get();
        Optional<Cart> cart = cartRepository.findByUser(user);
        Items item = new Items();
            Optional<Cycle> cycle = cycleRepository.findById(cycleId);
            cycle.get().setNumBorrowed(quantity);
            cycleRepository.save(cycle.get());
            if(cycle.isPresent()){
                item.setCycle(cycle.get());
                item.setQuantity(quantity);
            }
        if(cart.isPresent()){
            cart.get().getItems().add(item);
            cartRepository.save(cart.get());    
        }
        else{
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.getItems().add(item);
            cartRepository.save(newCart);
        }
        
        return cart.get().getItems();
    }
    public String removeFromCart(int cycleId,int quantity,String name){
        User user = userRepository.findByName(name).get();
        Optional<Cart> cart = cartRepository.findByUser(user);
        Items item = new Items();
            Optional<Cycle> cycle = cycleRepository.findById(cycleId);
            if(cycle.isPresent()){
                item.setCycle(cycle.get());
                item.setQuantity(quantity);
            }
            else{
                return "Cycle not present";
            }
        if(cart.isPresent()){
            cart.get().getItems().remove(item);
            return "Removed From Cart";
        }
        else{
            return "Cart Empty";
        }
    }
    public List<Items> checkOut(String name){
        User user = userRepository.findByName(name).get();
        Optional<Cart> cart = cartRepository.findByUser(user);
        List<Items> items = new ArrayList<>();
        if(cart.isPresent()){
            Optional<Bag> bag = bagRepository.findByUser(user);
            if(bag.isPresent()){
                bag.get().getItems().addAll(cart.get().getItems());
                items = bag.get().getItems();
            }
            else{
                Bag bag2 = new Bag();
                bag2.setUser(user);
                bag2.getItems().addAll(cart.get().getItems());
                items = bag2.getItems();
            }
            cartRepository.delete(cart.get());
            return items;
        }
        return null;
    }
}

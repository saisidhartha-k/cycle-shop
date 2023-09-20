package com.cycle.rental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String addToCart(int cycleId,int quantity,String name){
        User user = userRepository.findByName(name).get();
        Optional<Cart> cart = cartRepository.findByUser(user);
        List<Items> items = new ArrayList<>();
        Items item = new Items();
            Optional<Cycle> cycle = cycleRepository.findById(cycleId);
            if(cycle.get().getNumBorrowed() + quantity <= cycle.get().getStock()) {
            cycle.get().setNumBorrowed(cycle.get().getNumBorrowed()+quantity);
            }else {
            	return "OutOfStock";
            }
            cycleRepository.save(cycle.get());
            if(cycle.isPresent()){
                item.setCycle(cycle.get());
                item.setQuantity(quantity);
            }
        if(cart.isPresent()){
            items = cart.get().getItems().stream().filter(ite -> ite.getCycle().getCycleId()==cycleId).collect(Collectors.toList());
            if(items.size()==0){
                cart.get().getItems().add(item);
            }
            else{
                cart.get().getItems().removeAll(items);
                items.get(0).setQuantity(items.get(0).getQuantity()+quantity);
                cart.get().getItems().addAll(items);
            }
            cartRepository.save(cart.get());  
            items = cart.get().getItems();
        }
        else{
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.getItems().add(item);
            cartRepository.save(newCart);
            items = newCart.getItems();
        }
        
        return "Added To Cart";
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
            cart.get().getItems().stream()
            .peek(ite -> {
            if (ite.getCycle().getCycleId() == cycleId) {
            ite.getCycle().setNumBorrowed(ite.getCycle().getNumBorrowed() - quantity);
            ite.setQuantity(ite.getQuantity()-quantity);
            cycleRepository.save(ite.getCycle());
            }
            })
            .filter(ite -> ite.getCycle().getCycleId() != cycleId)
            .collect(Collectors.toList());
            
            cart.get().getItems().removeIf(ite -> {
                if (ite.getCycle().getCycleId() == cycleId) {
                    return ite.getQuantity() == 0;
                }
                return false;
            });
            
            cartRepository.save(cart.get());
            return "Removed From Cart";
            
        }
        else{
            return "Cart Empty";
        }
    }
    public List<Items> checkOut(String name,long amount){
        User user = userRepository.findByName(name).get();
        Optional<Cart> cart = cartRepository.findByUser(user);
        List<Items> items = new ArrayList<>();
        if(cart.isPresent()){
            Optional<Bag> bag = bagRepository.findByUser(user);
            if(bag.isPresent()){
                bag.get().getItems().addAll(cart.get().getItems());
                items = bag.get().getItems();
                bag.get().setTotalAmount(bag.get().getTotalAmount()+ amount);
                bagRepository.save(bag.get());
            }
            else{
                Bag bag2 = new Bag();
                bag2.setUser(user);
                bag2.getItems().addAll(cart.get().getItems());
                bag2.setTotalAmount(amount);
                items = bag2.getItems();
                bagRepository.save(bag2);
            }
            cartRepository.delete(cart.get());
            return items;
        }
        return null;
    }
    
    public List<Items> getCartItems(String username){
    	User user = userRepository.findByName(username).get();
    	try {
    	Optional<Cart> usercart = cartRepository.findByUser(user);
        return usercart.get().getItems();
    	}catch(Exception e) {
    		return null;
    	}
    }
}
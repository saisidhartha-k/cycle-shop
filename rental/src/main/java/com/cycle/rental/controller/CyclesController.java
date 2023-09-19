package com.cycle.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.cycle.rental.entity.Cycle;
import com.cycle.rental.entity.Items;
import com.cycle.rental.service.CartService;
import com.cycle.rental.service.CycleService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CyclesController  {

    @Autowired
    private CycleService cycleService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cycle> getCyclesList(Principal principal,Authentication authentication) {
        // Jwt jwt = (jwt) authentication.getprincipal();
        return cycleService.listAvailableCycles();
    }

    @PostMapping("/{id}/borrow")
    public String borrowCycle(@PathVariable int id,@RequestParam int count) {
        cycleService.borrowCycle(id, count);
        return "Added to Cart";
    }

    @PostMapping("/{id}/return")
    public String returnCycle(@PathVariable int id,@RequestParam int count) {
        cycleService.returnCycle(id, count);
        return "Returned to Cart";
    }

    @PutMapping("/add")
    public String addCycle(@RequestBody Cycle newCycle) {
       cycleService.save(newCycle);
       return "Added new Cycle";
    }

    @GetMapping("/restock")
    public Iterable<Cycle> getCyclesStock() {
        return cycleService.listAvailableCycles();
    }

    @PostMapping("/{id}/restock")
    public String addStock(@PathVariable int id,@RequestParam int count){
        cycleService.restockBy(id, count);
        return "Restocked Successfully";
    }

    @PostMapping("/{id}/add-cart")
    public List<Items> addToCart(@PathVariable int id,@RequestParam int count){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
        return cartService.addToCart(id, count,username);
    }

    @PostMapping("/{id}/remove-cart")
    public String removeFromCart(@PathVariable int id,@RequestParam int count){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
        return cartService.removeFromCart(id, count,username);
    }

    @PostMapping("/check-out")
    public List<Items> CheckOut(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
        return cartService.checkOut(username);
    }
    

}

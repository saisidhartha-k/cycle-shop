package com.cycle.rental.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cycle.rental.entity.Bag;
import com.cycle.rental.entity.Cycle;
import com.cycle.rental.entity.Items;
import com.cycle.rental.service.BagService;
import com.cycle.rental.service.CartService;
import com.cycle.rental.service.CycleService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RequestMapping("/api/cycles")
public class CyclesController {

	@Autowired
	private CycleService cycleService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private BagService bagService;

	@GetMapping
	public List<Cycle> getCyclesList(Principal principal, Authentication authentication) {
		// Jwt jwt = (jwt) authentication.getprincipal();
		return cycleService.listAvailableCycles();
	}

	@PostMapping("/{id}/borrow")
	public String borrowCycle(@PathVariable int id, @RequestParam int count) {
		cycleService.borrowCycle(id, count);
		return "Added to Cart";
	}

	@PostMapping("/{id}/return")
	public String returnCycle(@PathVariable int id, @RequestParam int count) {
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
	public String addStock(@PathVariable int id, @RequestParam int count) {
		cycleService.restockBy(id, count);
		return "Restocked Successfully";
	}

	@PostMapping("/{id}/add-cart")
	public String addToCart(@PathVariable int id, @RequestParam int count) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return cartService.addToCart(id, count, username);
	}

	@GetMapping("/get-cart")
	public List<Items> getCartItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return cartService.getCartItems(username);
	}

	@PostMapping("/{id}/remove-cart")
	public String removeFromCart(@PathVariable int id, @RequestParam int count) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return cartService.removeFromCart(id, count, username);
	}

	@PostMapping("/checkout")
	public String CheckOut(@RequestParam long amount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		cartService.checkOut(username,amount);
		return "success";
	}

	@GetMapping("/all-Purchases")

	public List<Bag> AdminPurchaseView() {

		return bagService.getAllBags();

	}

	@PostMapping("/return-Purchases")

	public String returnPurchases() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		return bagService.returnFromBag(username);

	}

    @PostMapping("/return-Purchases")
    public String returnPurchases(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
        return bagService.returnFromBag(username);
    }

}

import { Component, OnInit } from '@angular/core';
import { CycleService } from '../app.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cartItems: any[] = []; // Define an array to store cart items
  totalAmount: number = 0; // Initialize total amount to 0

  constructor(private cycleService: CycleService) {}

  ngOnInit(): void {
    // Fetch cart data from the service
    this.cycleService.getCartData().subscribe((data: any[]) => {
      // const mergedCartItems: any[] = [];
      // const itemMap = new Map<number, number>(); // Map cycleId to index in mergedCartItems

      // data.forEach((item) => {
      //   const cycleId = item.cycle.cycleId;
      //   if (itemMap.has(cycleId)) {
      //     // If an item with the same cycleId exists, increase the quantity
      //     mergedCartItems[itemMap.get(cycleId)!].quantity += item.quantity;
      //   } else {
      //     // Otherwise, add it to the mergedCartItems array and update the map
      //     itemMap.set(cycleId, mergedCartItems.length);
      //     mergedCartItems.push(item);
      //   }
      this.cartItems = data;
      // this.cartItems = mergedCartItems;
      this.calculateTotalAmount(); // Calculate the total amount
    });
  }

  calculateTotalAmount(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.cycle.price * item.quantity,
      0
    );
  }

  checkout(): void {
  this.cycleService.checkout().subscribe(

    (response: any) => {

      console.log('PUT request successful:', response);
      // this.cycleService.setData(response);
    },

    );  
    this.ngOnInit();
  }

  removeCartItem(cycleId:any): void {
    
    this.cycleService.removeCartItem().subscribe(

      (response: any) => {
  
        console.log('PUT request successful:', response);
        // this.cycleService.setData(response);
      },
  
      );
  }
}

import { Component, Input, OnInit } from '@angular/core';
import { Item } from 'src/app/Models/Item';
import { CartService } from 'src/app/Services/cart.service';

///Gets Cart information from the cart service and displays it to the user. Also contains logic for removing items
@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent implements OnInit {

  items: Item[] = [];
  
  @Input() cartTotal: number = this.CartService.total;

  totalString : String = '';


  constructor(private CartService: CartService) {}

  ngOnInit(): void {
    this.items = this.CartService.items;
    this.updateTotal();
  }

  updateTotal(){
    this.totalString = Number(this.CartService.total).toFixed(2);
  }

}

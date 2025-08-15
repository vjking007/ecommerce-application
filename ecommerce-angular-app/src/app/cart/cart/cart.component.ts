import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart/cart.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  userId = 1; // Get from auth service in real app

  constructor(
    private cartService: CartService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart() {
    this.cartService.getCart(this.userId).subscribe({
      next: (data) => this.cart = data,
      error: () => this.toastr.error('Failed to load cart')
    });
  }

  updateQuantity(productId: number, newQty: number) {
    if (newQty <= 0) return;
    this.cartService.updateQuantity(this.userId, productId, newQty).subscribe({
      next: () => {
        this.toastr.success('Quantity updated');
        this.loadCart();
      },
      error: () => this.toastr.error('Update failed')
    });
  }

  removeItem(productId: number) {
    this.cartService.removeItem(this.userId, productId).subscribe({
      next: () => {
        this.toastr.info('Item removed');
        this.loadCart();
      },
      error: () => this.toastr.error('Remove failed')
    });
  }
}

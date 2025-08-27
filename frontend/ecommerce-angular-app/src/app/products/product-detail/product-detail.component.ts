import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../product.service';
import { Product } from '../../shared/models/product.model';
import { CartService } from '../../cart/cart.service';
import { ToastrService } from 'ngx-toastr';
import { CartResponse } from 'src/app/shared/models/cart-response.model';
import { AddItemRequest } from 'src/app/shared/models/add-item-request.model';
import  { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

   product!: Product;
   cart: CartResponse | null = null;
   totalPrice: number = 0;
   selectedQuantity: number = 1; // Default 1 quantity

   constructor(private route: ActivatedRoute,
   private productService: ProductService,
   private cartService: CartService,
   private toastr: ToastrService,
   private authService: AuthService) {}

    ngOnInit(): void {
      const id = this.route.snapshot.params['id'];
      this.productService.getProductById(+id).subscribe(data => {
        this.product = data;
      });
    }

     addToCart(productId: number, quantity:number, price: number) {
     const userId=this.authService.getUserIdFromToken();
     console.log(userId);
     const request: AddItemRequest = { productId, quantity, price };
           this.cartService.addItem(userId, request).subscribe({
             next: (response) => {

              this.cart = response;
              this.calculateTotal();
              this.toastr.success('Item added to cart!');
              },
             error: () => this.toastr.error('Failed to add item')
           });
     }

      /** Calculate total price */
       calculateTotal() {
         if (this.cart?.items) {
           this.totalPrice = this.cart.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
         }
       }
}

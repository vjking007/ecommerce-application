import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../../shared/models/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

    constructor(private productService: ProductService, private router: Router) {}

    ngOnInit(): void {
      this.productService.getAllProducts().subscribe(data => {
        this.products = data;
      });
    }

    viewDetails(id: number) {
      this.router.navigate(['/products', id]);
    }
}

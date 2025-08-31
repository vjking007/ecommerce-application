import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: any[] = [];

    constructor(private adminService: AdminService) {}

    ngOnInit(): void {
      this.loadProducts();
    }

    loadProducts(): void {
      this.adminService.getProducts().subscribe(data => {
        this.products = data;
      });
    }

    deleteProduct(id: number): void {
      if (confirm('Are you sure you want to delete this product?')) {
        this.adminService.deleteProduct(id).subscribe(() => {
          this.loadProducts();
        });
      }
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../admin.service';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {

     product: any = { name: '', description:'', price: 1.0, stock: 0, category:'', active:false };
     isEdit = false;
     productId!: number;

     constructor(
       private adminService: AdminService,
       private route: ActivatedRoute,
       private router: Router
     ) {}

     ngOnInit(): void {
       this.productId = this.route.snapshot.params['id'];
       if (this.productId) {
         this.isEdit = true;
         this.adminService.getProduct(this.productId).subscribe({
                next: (res: any) => {
                  console.log("API Response:", res);
                  this.product = res;
                }
              });
       }
     }

    onSubmit(): void {
      if (this.isEdit) {
        this.adminService.updateProduct(this.productId, this.product).subscribe(() => {
          this.router.navigate(['/admin/products']);
        });
      } else {
      //save new product
        this.adminService.createProduct(this.product).subscribe({
            next:()=>{
                alert('Product added successfully');
                this.router.navigate(['/admin/products']);
                },
                error:(err)=>{
                console.error('Error saving product', err);
                alert('Something went wrong while saving the product.');
                }
        });
      }
    }

    toggleActive(product: any) {
      product.active = product.active;
     // product.isActive = product.isActive ? 1 : 0; // checkbox true/false convert into 1/0
    }
}

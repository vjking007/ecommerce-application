import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

    orders: any[] = [];

    constructor(private adminService: AdminService, private router: Router) {}

    ngOnInit(): void {
      this.loadOrders();
    }

    loadOrders() {
      this.adminService.getAllOrders().subscribe((res) => {
      console.log("HI "+res);
        this.orders = res;
      });
    }

    viewOrderDetails(orderId: number) {
      this.router.navigate(['/admin/orders', orderId]);
    }
}

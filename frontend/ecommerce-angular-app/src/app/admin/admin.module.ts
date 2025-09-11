import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ProductFormComponent } from './product-management/product-form/product-form.component';
import { ProductListComponent } from './product-management/product-list/product-list.component';
import { OrderListComponent } from './order-management/order-list/order-list.component';

@NgModule({
  declarations: [
    ProductListComponent,
    ProductFormComponent,
    AdminDashboardComponent,
    OrderListComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule
  ]
})
export class AdminModule { }

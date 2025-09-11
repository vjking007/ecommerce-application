import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-management/product-list/product-list.component';
import { ProductFormComponent } from './product-management/product-form/product-form.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { OrderListComponent } from './order-management/order-list/order-list.component';


const routes: Routes = [{ path: '', component: AdminDashboardComponent,
 children: [
       { path: 'products', component: ProductListComponent },
       { path: 'products/new', component: ProductFormComponent },
       { path: 'products/edit/:id', component: ProductFormComponent },
       { path: 'orders', component: OrderListComponent }
   ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }

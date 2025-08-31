import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-management/product-list/product-list.component';
import { ProductFormComponent } from './product-management/product-form/product-form.component';

const routes: Routes = [{ path: 'products', children: [
       { path: '', component: ProductListComponent },
       { path: 'new', component: ProductFormComponent },
       { path: 'edit/:id', component: ProductFormComponent }
   ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }

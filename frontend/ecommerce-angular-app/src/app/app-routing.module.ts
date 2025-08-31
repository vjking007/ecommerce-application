import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { AuthGuard } from './core/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'products', canActivate: [AuthGuard], loadChildren: () => import('./products/products.module').then(m => m.ProductsModule) },
  { path: 'admin', canActivate: [AuthGuard],loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

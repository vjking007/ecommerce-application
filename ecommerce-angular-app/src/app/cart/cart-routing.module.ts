import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// This code defines the routing for the cart module in an Angular application.
// It imports necessary modules and the CartComponent, then sets up a route for the cart path.
// The CartComponent will be displayed when the user navigates to the 'cart' path.
// Finally, it exports the RouterModule configured with the defined routes.
// This allows the cart module to be lazy-loaded or used in the main application routing.

import { CartComponent } from './cart/cart.component';

const routes: Routes = [
{ path: 'cart', component: CartComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CartRoutingModule { }

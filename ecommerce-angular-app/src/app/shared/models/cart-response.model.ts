export interface CartResponse {
  cartId: number;
  userId: number;
  items: CartItemResponse[];
  subTotal: number;
}

export interface CartItemResponse {
  cartItemId: number;
  productId: number;
  quantity: number;
  price: number;
  totalPrice: number;
 }


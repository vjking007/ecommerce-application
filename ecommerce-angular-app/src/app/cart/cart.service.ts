import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartResponse } from '../shared/models/cart-response.model';
import { AddItemRequest } from '../shared/models/add-item-request.model';
import { ToastrService } from 'ngx-toastr';


@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl = 'http://localhost:9090/api/cart'; // cart-service URL

    constructor(private http: HttpClient) {}

    addItem(userId: number , req: AddItemRequest): Observable<CartResponse> {

     const headers = new HttpHeaders({
          'X-User-Id': userId.toString()
        });
     console.log(`Adding item to cart for userId=${userId} -> ${this.baseUrl}/add`, req);
     return this.http.post<CartResponse>(`${this.baseUrl}/add`, req, { headers });
    }

    getCart(userId: number): Observable<CartResponse> {
      return this.http.get<CartResponse>(`${this.baseUrl}/${userId}`);
    }

    updateQuantity(userId: number, productId: number, quantity: number): Observable<CartResponse> {
      return this.http.put<CartResponse>(`${this.baseUrl}/${userId}/items/${productId}`, { quantity });
    }

    removeItem(userId: number, productId: number): Observable<CartResponse> {
       return this.http.delete<CartResponse>(`${this.baseUrl}/${userId}/items/${productId}`);
    }
}

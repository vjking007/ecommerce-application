import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:9090/api';

  constructor(private http: HttpClient) {}

  // ✅ Product CRUD
  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/products/all`);
  }

  getProduct(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/products/${id}`);
  }

  createProduct(product: any): Observable<any> {
  console.log(product);
    return this.http.post<any>(`${this.baseUrl}/products/save`, product);
  }

  updateProduct(id: number, product: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/products/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/products/${id}`);
  }

  // ✅ Orders
  getAllOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/orders`);
  }

  getOrder(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/orders/${id}`);
  }
}

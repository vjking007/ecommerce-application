import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from 'src/app/shared/models/product.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

 private baseUrl = 'http://localhost:9090/api/products';

   constructor(private http: HttpClient) {}

   getAllProducts(): Observable<Product[]> {
     return this.http.get<Product[]>(`${this.baseUrl}/all`);
   }

   getProductById(id: number): Observable<Product> {
     return this.http.get<Product>(`${this.baseUrl}/${id}`);
   }
}

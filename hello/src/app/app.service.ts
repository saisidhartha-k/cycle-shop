import { Injectable } from '@angular/core';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class CycleService {

  constructor(private _http:HttpClient)
{}

// cartData: any;

// setData(data: any) {
//   this.cartData = data;
// }

// getData() {
//   return this.cartData;
// }

getCycles(): Observable<any>
{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  return this._http.get('http://localhost:8080/api/cycles',  {headers: headers}) ;
}

getBorrowedCycles()
{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  return this._http.get('http://localhost:8080/api/cycles/borrowed' ,  {headers: headers})
}

borrowCycles(cycleId: number, quantity: number) {

  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/borrow/${cycleId}`;

  return this._http.post(apiUrl, {"quantity":quantity},  {headers: headers});

}

addToCart(cycleId: number, quantity: number) {

  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/${cycleId}/add-cart?count=${quantity}`;

  return this._http.post(apiUrl, {},  {headers: headers,responseType : "text"});

}

returnCycles(cycleId: number)
{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/return/${cycleId}`;
  return this._http.post(apiUrl, {},   {headers: headers});
}

addCycle(newCycle: any): Observable<any> {

  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = 'http://localhost:8080/api/cycles/add'; 

  return this._http.put(apiUrl, newCycle, {headers: headers});
}

getCartData(): Observable<any>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = 'http://localhost:8080/api/cycles/get-cart'; 

  return this._http.get(apiUrl,{headers: headers});
}

checkout(totalAmount:number): Observable<any>{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = 'http://localhost:8080/api/cycles/checkout'; 

  return this._http.post(`${apiUrl}?amount=${totalAmount}`,{},{headers: headers,responseType:"text"});
}

removeCartItem(cycleId:any,quantity:number): Observable<any>{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/${cycleId}/remove-cart?count=${quantity}`;
  return this._http.post(apiUrl, {}, {headers: headers,responseType:"text"});
}

getRentalRecord(): Observable<any>{
  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/all-Purchases`;
  return this._http.get(apiUrl,{headers: headers});
}

}

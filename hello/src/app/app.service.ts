import { Injectable } from '@angular/core';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class CycleService {

  constructor(private _http:HttpClient)
{}

getCycles()
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

borrowCycles(cycleId: number) {

  const headers = new HttpHeaders({
    'Authorization': 'Bearer ' + localStorage.getItem('token') 
  });
  const apiUrl = `http://localhost:8080/api/cycles/borrow/${cycleId}`;

  return this._http.post(apiUrl, {},   {headers: headers});

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

  return this._http.put(apiUrl, newCycle);
}



}

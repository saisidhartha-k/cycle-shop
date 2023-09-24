import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  authUrl = 'http://localhost:8080/api/auth';



  login(username: string, password: string): Observable<any> {

    return this.http.post<any>(`${this.authUrl}/token`, { username: username, password: password }).pipe(

      tap(res => {

        localStorage.setItem('token', res['token']);
        console.log(res['token']);
        localStorage.setItem('username', res['username']);
      })

    );

  }

  logout(): void {
    localStorage.setItem('token', '');
    localStorage.setItem('username', '');
  }

}

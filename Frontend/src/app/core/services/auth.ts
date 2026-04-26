import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(email: string, senha: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, senha }).pipe(
      tap((response: any) => {
        localStorage.setItem('token', response.token);
      })
    );
  }

  registro(dados: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, dados);
  }

  me(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLogado(): boolean {
    return !!this.getToken();
  }

}
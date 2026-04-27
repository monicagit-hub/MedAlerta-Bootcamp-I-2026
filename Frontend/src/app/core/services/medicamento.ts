import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {

  private apiUrl = 'http://localhost:8080/medicamentos';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  salvar(medicamento: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, medicamento);
  }

  atualizar(id: number, medicamento: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, medicamento);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
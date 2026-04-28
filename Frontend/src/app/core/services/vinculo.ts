import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VinculoService {

  private apiUrl = 'http://localhost:8080/vinculos';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  salvar(idUsuario: number, idMedicamento: number, dosagem: string, formaUso: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}?idUsuario=${idUsuario}&idMedicamento=${idMedicamento}&dosagem=${dosagem}&formaUso=${formaUso}`,
      {}
    );
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
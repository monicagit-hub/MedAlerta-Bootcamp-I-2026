import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlertaService {

  private apiUrl = 'http://localhost:8080/alertas';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  salvar(idHorario: number, dataHorarioAlerta: string, statusAlerta: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}?idHorario=${idHorario}&dataHorarioAlerta=${dataHorarioAlerta}&statusAlerta=${statusAlerta}`,
      {}
    );
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
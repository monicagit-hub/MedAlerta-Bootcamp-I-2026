import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  private apiUrl = 'http://localhost:8080/registros';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  salvar(idAlerta: number, dataHorarioConsumo: string, confirmacaoConsumo: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}?idAlerta=${idAlerta}&dataHorarioConsumo=${dataHorarioConsumo}&confirmacaoConsumo=${confirmacaoConsumo}`,
      {}
    );
}

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
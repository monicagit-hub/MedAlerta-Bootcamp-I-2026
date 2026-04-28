import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HorarioService {

  private apiUrl = 'http://localhost:8080/horarios';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  salvar(idUsuarioMedicamento: number, horarioUso: string, frequenciaUso: string): Observable<any> {
    return this.http.post<any>(
      `${this.apiUrl}?idUsuarioMedicamento=${idUsuarioMedicamento}&horarioUso=${horarioUso}&frequenciaUso=${frequenciaUso}`,
      {}
    );
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
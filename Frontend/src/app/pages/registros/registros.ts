import { Component, OnInit } from '@angular/core';
import { RegistroService } from '../../core/services/registro';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-registros',
  imports: [Navbar],
  templateUrl: './registros.html',
  styleUrl: './registros.scss'
})
export class Registros implements OnInit {

  registros: any[] = [];
  carregando = true;

  constructor(private registroService: RegistroService) {}

  ngOnInit() {
    this.registroService.listarTodos().subscribe({
      next: (dados) => {
        this.registros = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });
  }

  formatarDataHora(dataHora: string): string {
    if (!dataHora) return '—';
    const data = new Date(dataHora);
    return data.toLocaleString('pt-BR', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

}

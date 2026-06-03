import { Component, OnInit } from '@angular/core';
import { AlertaService } from '../../core/services/alerta';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-alertas',
  imports: [Navbar],
  templateUrl: './alertas.html',
  styleUrl: './alertas.scss'
})
export class Alertas implements OnInit {

  alertas: any[] = [];
  carregando = true;

  constructor(private alertaService: AlertaService) {}

  get pendentes() {
    return this.alertas.filter(a => a.statusAlerta === 'emitido');
  }

  get historico() {
    return this.alertas.filter(a => a.statusAlerta !== 'emitido');
  }

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.carregando = true;
    this.alertaService.listarTodos().subscribe({
      next: (dados) => {
        this.alertas = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });
  }

  confirmar(id: number) {
    this.alertaService.confirmar(id).subscribe({
      next: () => this.carregar(),
      error: () => alert('Erro ao confirmar alerta.')
    });
  }

  cancelar(id: number) {
    this.alertaService.cancelar(id).subscribe({
      next: () => this.carregar(),
      error: () => alert('Erro ao cancelar alerta.')
    });
  }

  formatarHorario(dataHorario: string): string {
    if (!dataHorario) return '';
    const data = new Date(dataHorario);
    return data.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
  }

}

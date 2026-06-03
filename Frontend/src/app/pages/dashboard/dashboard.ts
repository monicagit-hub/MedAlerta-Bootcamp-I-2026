import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { AuthService } from '../../core/services/auth';
import { MedicamentoService } from '../../core/services/medicamento';
import { HorarioService } from '../../core/services/horario';
import { AlertaService } from '../../core/services/alerta';
import { RegistroService } from '../../core/services/registro';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, Navbar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {

  usuario: any = null;

  contadores = {
    medicamentos: 0,
    horarios: 0,
    alertasPendentes: 0,
    registros: 0
  };

  constructor(
    private authService: AuthService,
    private medicamentoService: MedicamentoService,
    private horarioService: HorarioService,
    private alertaService: AlertaService,
    private registroService: RegistroService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.me().subscribe({
      next: (dados) => {
        this.usuario = dados;
        this.carregarContadores();
      },
      error: () => this.logout()
    });
  }

  carregarContadores() {
    forkJoin({
      medicamentos: this.medicamentoService.listarTodos(),
      horarios: this.horarioService.listarTodos(),
      alertas: this.alertaService.listarTodos(),
      registros: this.registroService.listarTodos()
    }).subscribe({
      next: ({ medicamentos, horarios, alertas, registros }) => {
        this.contadores.medicamentos = medicamentos.length;
        this.contadores.horarios = horarios.length;
        this.contadores.alertasPendentes = alertas.filter(a => a.statusAlerta === 'emitido').length;
        this.contadores.registros = registros.length;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}

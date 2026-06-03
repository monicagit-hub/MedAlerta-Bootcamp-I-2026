import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';
import { AlertaService } from '../../services/alerta';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar implements OnInit, OnDestroy {

  usuario: any = null;
  alertasPendentes = 0;

  private intervalo: any;

  constructor(
    private authService: AuthService,
    private alertaService: AlertaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.me().subscribe({
      next: (dados) => {
        this.usuario = dados;
        this.atualizarAlertas();
        // Sincronizado com o scheduler (60s)
        this.intervalo = setInterval(() => this.atualizarAlertas(), 60000);
      },
      error: () => {}
    });
  }

  ngOnDestroy() {
    if (this.intervalo) {
      clearInterval(this.intervalo);
    }
  }

  atualizarAlertas() {
    this.alertaService.listarTodos().subscribe({
      next: (alertas) => {
        this.alertasPendentes = alertas.filter(a => a.statusAlerta === 'emitido').length;
      },
      error: () => {}
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}

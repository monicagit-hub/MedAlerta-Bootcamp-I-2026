import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../core/services/usuario';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-usuarios',
  imports: [RouterLink],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.scss'
})
export class Usuarios implements OnInit {

  usuarios: any[] = [];
  carregando = true;

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.usuarioService.listarTodos().subscribe({
      next: (dados) => {
        this.usuarios = dados;
        this.carregando = false;
      },
      error: () => {
        this.carregando = false;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
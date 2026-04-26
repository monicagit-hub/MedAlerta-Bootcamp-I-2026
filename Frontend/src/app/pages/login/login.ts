import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';


@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  email = '';
  senha = '';
  erro = '';
  carregando = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    this.erro = '';
    this.carregando = true;

    this.authService.login(this.email, this.senha).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.erro = 'Email ou senha inválidos!';
        this.carregando = false;
      }
    });
  }

}
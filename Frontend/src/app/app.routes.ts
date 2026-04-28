import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Usuarios } from './pages/usuarios/usuarios';
import { Medicamentos } from './pages/medicamentos/medicamentos';
import { Vinculos } from './pages/vinculos/vinculos';
import { Horarios } from './pages/horarios/horarios';


export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'usuarios', component: Usuarios, canActivate: [authGuard] },
  { path: 'medicamentos', component: Medicamentos, canActivate: [authGuard] },
  { path: 'vinculos', component: Vinculos, canActivate: [authGuard] },
  { path: 'horarios', component: Horarios, canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' }
];
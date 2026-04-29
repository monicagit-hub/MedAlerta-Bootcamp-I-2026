import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AlertaService } from '../../core/services/alerta';
import { HorarioService } from '../../core/services/horario';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-alertas',
  imports: [FormsModule, Navbar],
  templateUrl: './alertas.html',
  styleUrl: './alertas.scss'
})
export class Alertas implements OnInit {

  alertas: any[] = [];
  horarios: any[] = [];
  carregando = true;
  mostrarFormulario = false;

  formulario = {
    idHorario: '',
    dataHorarioAlerta: '',
    statusAlerta: 'emitido'
  };

  constructor(
    private alertaService: AlertaService,
    private horarioService: HorarioService
  ) {}

  ngOnInit() {
    this.carregarDados();
  }

  carregarDados() {
    this.alertaService.listarTodos().subscribe({
      next: (dados) => {
        this.alertas = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });

    this.horarioService.listarTodos().subscribe({
      next: (dados) => this.horarios = dados
    });
  }

  salvar() {
    this.alertaService.salvar(
      Number(this.formulario.idHorario),
      this.formulario.dataHorarioAlerta,
      this.formulario.statusAlerta
    ).subscribe({
      next: () => {
        this.mostrarFormulario = false;
        this.formulario = { idHorario: '', dataHorarioAlerta: '', statusAlerta: 'emitido' };
        this.carregarDados();
      },
      error: () => alert('Erro ao salvar alerta!')
    });
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este alerta?')) {
      this.alertaService.deletar(id).subscribe({
        next: () => {
          this.alertas = this.alertas.filter(a => a.idAlerta !== id);
        },
        error: () => alert('Erro ao deletar alerta!')
      });
    }
  }

}
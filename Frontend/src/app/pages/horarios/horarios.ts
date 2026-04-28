import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HorarioService } from '../../core/services/horario';
import { VinculoService } from '../../core/services/vinculo';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-horarios',
  imports: [FormsModule, Navbar],
  templateUrl: './horarios.html',
  styleUrl: './horarios.scss'
})
export class Horarios implements OnInit {

  horarios: any[] = [];
  vinculos: any[] = [];
  carregando = true;
  mostrarFormulario = false;

  formulario = {
    idUsuarioMedicamento: '',
    horarioUso: '',
    frequenciaUso: ''
  };

  constructor(
    private horarioService: HorarioService,
    private vinculoService: VinculoService
  ) {}

  ngOnInit() {
    this.carregarDados();
  }

  carregarDados() {
    this.horarioService.listarTodos().subscribe({
      next: (dados) => {
        this.horarios = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });

    this.vinculoService.listarTodos().subscribe({
      next: (dados) => this.vinculos = dados
    });
  }



 salvar() {
    this.horarioService.salvar(
      Number(this.formulario.idUsuarioMedicamento),
      this.formulario.horarioUso,
      this.formulario.frequenciaUso
    ).subscribe({
      next: () => {
        this.mostrarFormulario = false;
        this.formulario = { idUsuarioMedicamento: '', horarioUso: '', frequenciaUso: '' };
        this.carregarDados();
      },
      error: () => alert('Erro ao salvar horário!')
    });
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este horário?')) {
      this.horarioService.deletar(id).subscribe({
        next: () => {
          this.horarios = this.horarios.filter(h => h.idHorario !== id);
        },
        error: () => alert('Erro ao deletar horário!')
      });
    }
  }

}
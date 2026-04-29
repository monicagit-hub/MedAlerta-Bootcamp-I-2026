import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RegistroService } from '../../core/services/registro';
import { AlertaService } from '../../core/services/alerta';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-registros',
  imports: [FormsModule, Navbar],
  templateUrl: './registros.html',
  styleUrl: './registros.scss'
})
export class Registros implements OnInit {

  registros: any[] = [];
  alertas: any[] = [];
  carregando = true;
  mostrarFormulario = false;

  formulario = {
  idAlerta: '',
  dataHorarioConsumo: '',
  confirmacaoConsumo: 'sim'
};

  constructor(
    private registroService: RegistroService,
    private alertaService: AlertaService
  ) {}

  ngOnInit() {
    this.carregarDados();
  }

  carregarDados() {
    this.registroService.listarTodos().subscribe({
      next: (dados) => {
        this.registros = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });

    this.alertaService.listarTodos().subscribe({
      next: (dados) => this.alertas = dados
    });
  }

  salvar() {
    this.registroService.salvar(
      Number(this.formulario.idAlerta),
      this.formulario.dataHorarioConsumo,
      this.formulario.confirmacaoConsumo
    ).subscribe({

        next: () => {
          this.mostrarFormulario = false;
          this.formulario = { idAlerta: '', dataHorarioConsumo: '', confirmacaoConsumo: 'sim' };
          this.carregarDados();
        },
        error: () => alert('Erro ao salvar registro!')
      });
    }



  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este registro?')) {
      this.registroService.deletar(id).subscribe({
        next: () => {
          this.registros = this.registros.filter(r => r.idRegistro !== id);
        },
        error: () => alert('Erro ao deletar registro!')
      });
    }
  }

}
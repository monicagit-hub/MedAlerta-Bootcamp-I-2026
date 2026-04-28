import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MedicamentoService } from '../../core/services/medicamento';
import { Navbar } from '../../core/components/navbar/navbar';


@Component({
  selector: 'app-medicamentos',
  imports: [FormsModule, Navbar],
  templateUrl: './medicamentos.html',
  styleUrl: './medicamentos.scss'
})
export class Medicamentos implements OnInit {

  medicamentos: any[] = [];
  carregando = true;
  mostrarFormulario = false;
  editando = false;
  idEditando: number | null = null;

  formulario = {
    nomeComercial: '',
    nomeGenerico: '',
    formaUso: '',
    observacao: '',
    quantidade: ''
  };

  constructor(
    private medicamentoService: MedicamentoService,
  ) {}

  ngOnInit() {
    this.carregarMedicamentos();
  }

  carregarMedicamentos() {
    this.medicamentoService.listarTodos().subscribe({
      next: (dados) => {
        this.medicamentos = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });
  }

  abrirFormulario() {
    this.editando = false;
    this.idEditando = null;
    this.formulario = { nomeComercial: '', nomeGenerico: '', formaUso: '', observacao: '', quantidade: '' };
    this.mostrarFormulario = true;
  }

  editar(med: any) {
    this.editando = true;
    this.idEditando = med.idMedicamento;
    this.formulario = {
      nomeComercial: med.nomeComercial,
      nomeGenerico: med.nomeGenerico,
      formaUso: med.formaUso,
      observacao: med.observacao,
      quantidade: med.quantidade
    };
    this.mostrarFormulario = true;
  }

  salvar() {
    if (this.editando && this.idEditando) {
      this.medicamentoService.atualizar(this.idEditando, this.formulario).subscribe({
        next: () => {
          this.mostrarFormulario = false;
          this.carregarMedicamentos();
        },
        error: () => alert('Erro ao atualizar medicamento!')
      });
    } else {
      this.medicamentoService.salvar(this.formulario).subscribe({
        next: () => {
          this.mostrarFormulario = false;
          this.carregarMedicamentos();
        },
        error: () => alert('Erro ao salvar medicamento!')
      });
    }
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este medicamento?')) {
      this.medicamentoService.deletar(id).subscribe({
        next: () => {
          this.medicamentos = this.medicamentos.filter(m => m.idMedicamento !== id);
        },
        error: () => alert('Erro ao deletar medicamento!')
      });
    }
  }
}
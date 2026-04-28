import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { VinculoService } from '../../core/services/vinculo';
import { UsuarioService } from '../../core/services/usuario';
import { MedicamentoService } from '../../core/services/medicamento';
import { Navbar } from '../../core/components/navbar/navbar';

@Component({
  selector: 'app-vinculos',
  imports: [FormsModule, Navbar],
  templateUrl: './vinculos.html',
  styleUrl: './vinculos.scss'
})
export class Vinculos implements OnInit {

  vinculos: any[] = [];
  usuarios: any[] = [];
  medicamentos: any[] = [];
  carregando = true;
  mostrarFormulario = false;

  formulario = {
    idUsuario: '',
    idMedicamento: '',
    dosagem: '',
    formaUso: ''
  };

  constructor(
    private vinculoService: VinculoService,
    private usuarioService: UsuarioService,
    private medicamentoService: MedicamentoService
  ) {}

  ngOnInit() {
    this.carregarDados();
  }

  carregarDados() {
    this.vinculoService.listarTodos().subscribe({
      next: (dados) => {
        this.vinculos = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });

    this.usuarioService.listarTodos().subscribe({
      next: (dados) => this.usuarios = dados
    });

    this.medicamentoService.listarTodos().subscribe({
      next: (dados) => this.medicamentos = dados
    });
  }

  salvar() {
    this.vinculoService.salvar(
      Number(this.formulario.idUsuario),
      Number(this.formulario.idMedicamento),
      this.formulario.dosagem,
      this.formulario.formaUso
    ).subscribe({
      next: () => {
        this.mostrarFormulario = false;
        this.formulario = { idUsuario: '', idMedicamento: '', dosagem: '', formaUso: '' };
        this.carregarDados();
      },
      error: () => alert('Erro ao salvar vínculo!')
    });
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este vínculo?')) {
      this.vinculoService.deletar(id).subscribe({
        next: () => {
          this.vinculos = this.vinculos.filter(v => v.idUsuarioMedicamento !== id);
        },
        error: () => alert('Erro ao deletar vínculo!')
      });
    }
  }

}
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../core/services/usuario';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../core/components/navbar/navbar';


@Component({
  selector: 'app-usuarios',
  imports: [FormsModule,Navbar],
  templateUrl: './usuarios.html',
  styleUrl: './usuarios.scss'
})
export class Usuarios implements OnInit {

  usuarios: any[] = [];
  carregando = true;
  mostrarFormulario = false;
  editando = false;
  idEditando: number | null = null;

  formulario = {
    nome: '',
    telefone: '',
    email: '',
    senha: '',
    role: 'USER',
    enderecoRua: '',
    enderecoNumero: null,
    enderecoComplemento: '',
    enderecoBairro: '',
    enderecoCEP: '',
    enderecoCidade: '',
    enderecoEstado: ''
  };

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.carregarUsuarios();
  }

  abrirFormularioCadastro() {
    this.editando = false;
    this.idEditando = null;
    this.formulario = {
      nome: '',
      telefone: '',
      email: '',
      senha: '',
      role: 'USER',
      enderecoRua: '',
      enderecoNumero: null,
      enderecoComplemento: '',
      enderecoBairro: '',
      enderecoCEP: '',
      enderecoCidade: '',
      enderecoEstado: ''
    };
    this.mostrarFormulario = true;
  }

  carregarUsuarios() {
    this.usuarioService.listarTodos().subscribe({
      next: (dados) => {
        this.usuarios = dados;
        this.carregando = false;
      },
      error: () => this.carregando = false
    });
  }

  editar(usuario: any) {
    this.editando = true;
    this.idEditando = usuario.idUsuario;
    this.formulario = {
      nome: usuario.nome,
      telefone: usuario.telefone,
      email: usuario.email,
      senha: '',
      role: usuario.role,
      enderecoRua: usuario.enderecoRua || '',
      enderecoNumero: usuario.enderecoNumero || null,
      enderecoComplemento: usuario.enderecoComplemento || '',
      enderecoBairro: usuario.enderecoBairro || '',
      enderecoCEP: usuario.enderecoCEP || '',
      enderecoCidade: usuario.enderecoCidade || '',
      enderecoEstado: usuario.enderecoEstado || ''
    };
    this.mostrarFormulario = true;
  }

  salvar() {
    if (this.editando && this.idEditando) {
      this.usuarioService.atualizar(this.idEditando, this.formulario).subscribe({
        next: () => {
          this.mostrarFormulario = false;
          this.carregarUsuarios();
        },
        error: () => alert('Erro ao atualizar usuário!')
      });
    } else {
      this.authService.registro(this.formulario).subscribe({
        next: () => {
          this.mostrarFormulario = false;
          this.carregarUsuarios();
        },
        error: () => alert('Erro ao cadastrar usuário!')
      });
    }
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja deletar este usuário?')) {
      this.usuarioService.deletar(id).subscribe({
        next: () => {
          this.usuarios = this.usuarios.filter(u => u.idUsuario !== id);
        },
        error: () => alert('Erro ao deletar usuário!')
      });
    }
  }
}
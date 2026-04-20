package br.uninter.medalerta.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.service.MedicamentoService;
import br.uninter.medalerta.service.UsuarioService;

import java.util.Scanner;

@Component
public class App implements CommandLineRunner {

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Override
    public void run(String... args) throws Exception {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== MedAlerta ===");
            System.out.println("1. Usuários");
            System.out.println("2. Medicamentos");
            System.out.println("3. Vínculos");
            System.out.println("4. Horários");
            System.out.println("5. Alertas");
            System.out.println("6. Registros");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> menuUsuario();
                case 2 -> menuMedicamento();
                case 3 -> System.out.println(">> Vínculos");
                case 4 -> System.out.println(">> Horários");
                case 5 -> System.out.println(">> Alertas");
                case 6 -> System.out.println(">> Registros");
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

        private void menuUsuario() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== Usuários ===");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Cadastrar");
            System.out.println("4. Deletar");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> usuarioService.listarTodos()
                        .forEach(u -> System.out.println(u));
                case 2 -> {
                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();
                    System.out.println(usuarioService.buscarPorId(id));
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    usuarioService.salvar(new Usuario(nome, telefone, email));
                    System.out.println("Usuário cadastrado!");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();
                    usuarioService.deletar(id);
                    System.out.println("Usuário deletado!");
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }

        }
    }


        private void menuMedicamento() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== Medicamentos ===");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Cadastrar");
            System.out.println("4. Deletar");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> medicamentoService.listarTodos()
                        .forEach(m -> System.out.println(m));
                case 2 -> {
                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();
                    System.out.println(medicamentoService.buscarPorId(id));
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.print("Nome comercial: ");
                    String nomeComercial = scanner.nextLine();
                    System.out.print("Nome genérico: ");
                    String nomeGenerico = scanner.nextLine();
                    medicamentoService.salvar(new Medicamento(nomeComercial, nomeGenerico));
                    System.out.println("Medicamento cadastrado!");
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();
                    medicamentoService.deletar(id);
                    System.out.println("Medicamento deletado!");
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }
        

}
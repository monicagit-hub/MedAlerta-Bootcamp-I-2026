package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listarTodos() {
    return repository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado! ID: " + id));
    }

    public Usuario salvar(Usuario usuario) {
    return repository.save(usuario);
    }

    public Usuario atualizar(Integer id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        existente.setNome(usuario.getNome());
        existente.setTelefone(usuario.getTelefone());
        existente.setEmail(usuario.getEmail());
        existente.setEnderecoRua(usuario.getEnderecoRua());
        existente.setEnderecoNumero(usuario.getEnderecoNumero());
        existente.setEnderecoComplemento(usuario.getEnderecoComplemento());
        existente.setEnderecoBairro(usuario.getEnderecoBairro());
        existente.setEnderecoCEP(usuario.getEnderecoCEP());
        existente.setEnderecoCidade(usuario.getEnderecoCidade());
        existente.setEnderecoEstado(usuario.getEnderecoEstado());
        existente.setRole(usuario.getRole());
        return repository.save(existente);
    }

    public void deletar(Integer id) {
    buscarPorId(id); // verifica se existe antes de deletar
    repository.deleteById(id);
    }

}
package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveListarTodosOsUsuarios() {
        // Arrange — prepara os dados
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        when(repository.findAll()).thenReturn(List.of(usuario));

        // Act — executa o método
        List<Usuario> resultado = usuarioService.listarTodos();

        // Assert — verifica o resultado
        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        when(repository.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(999));
    }

}
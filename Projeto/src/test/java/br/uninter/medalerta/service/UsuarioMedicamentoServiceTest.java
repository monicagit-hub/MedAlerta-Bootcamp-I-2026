package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.UsuarioMedicamentoRepository;
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
public class UsuarioMedicamentoServiceTest {

    @Mock
    private UsuarioMedicamentoRepository repository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private MedicamentoService medicamentoService;

    @InjectMocks
    private UsuarioMedicamentoService usuarioMedicamentoService;

    @Test
    void deveListarTodosOsVinculos() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        when(repository.findAll()).thenReturn(List.of(vinculo));

        // Act
        List<UsuarioMedicamento> resultado = usuarioMedicamentoService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getUsuario().getNome());
    }
    @Test
    void deveBuscarVinculoPorId() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        when(repository.findById(1)).thenReturn(Optional.of(vinculo));

        // Act
        UsuarioMedicamento resultado = usuarioMedicamentoService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getUsuario().getNome());
    }
    @Test
    void deveLancarExcecaoQuandoVinculoNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioMedicamentoService.buscarPorId(999));
    }

}
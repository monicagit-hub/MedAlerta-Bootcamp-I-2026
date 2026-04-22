package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.HorarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HorarioServiceTest {

    @Mock
    private HorarioRepository repository;

    @Mock
    private UsuarioMedicamentoService usuarioMedicamentoService;

    @InjectMocks
    private HorarioService horarioService;

    @Test
    void deveListarTodosOsHorarios() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        when(repository.findAll()).thenReturn(List.of(horario));

        // Act
        List<Horario> resultado = horarioService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(LocalTime.of(8, 0), resultado.get(0).getHorarioUso());
    }

    @Test
    void deveBuscarHorarioPorId() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        when(repository.findById(1)).thenReturn(Optional.of(horario));

        // Act
        Horario resultado = horarioService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(LocalTime.of(8, 0), resultado.getHorarioUso());
    }

    @Test
    void deveLancarExcecaoQuandoHorarioNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> horarioService.buscarPorId(999));
    }

}
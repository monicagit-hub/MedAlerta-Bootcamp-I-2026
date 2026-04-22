package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.AlertaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private AlertaService alertaService;

    @Test
    void deveListarTodosOsAlertas() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        Alerta alerta = new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
        when(repository.findAll()).thenReturn(List.of(alerta));

        // Act
        List<Alerta> resultado = alertaService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(Alerta.StatusAlertaEnum.emitido, resultado.get(0).getStatusAlerta());
    }

    @Test
    void deveBuscarAlertaPorId() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        Alerta alerta = new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
        when(repository.findById(1)).thenReturn(Optional.of(alerta));

        // Act
        Alerta resultado = alertaService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(Alerta.StatusAlertaEnum.emitido, resultado.getStatusAlerta());
    }

    @Test
    void deveLancarExcecaoQuandoAlertaNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> alertaService.buscarPorId(999));
    }

}
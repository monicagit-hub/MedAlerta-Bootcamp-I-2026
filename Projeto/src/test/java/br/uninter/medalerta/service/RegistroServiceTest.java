package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.RegistroRepository;
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
public class RegistroServiceTest {

    @Mock
    private RegistroRepository repository;

    @Mock
    private AlertaService alertaService;

    @InjectMocks
    private RegistroService registroService;

    @Test
    void deveListarTodosOsRegistros() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        Alerta alerta = new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
        Registro registro = new Registro(alerta, Registro.ConfirmacaoConsumoEnum.sim);
        when(repository.findAll()).thenReturn(List.of(registro));

        // Act
        List<Registro> resultado = registroService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(Registro.ConfirmacaoConsumoEnum.sim, resultado.get(0).getConfirmacaoConsumo());
    }

    @Test
    void deveBuscarRegistroPorId() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        Alerta alerta = new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
        Registro registro = new Registro(alerta, Registro.ConfirmacaoConsumoEnum.sim);
        when(repository.findById(1)).thenReturn(Optional.of(registro));

        // Act
        Registro resultado = registroService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(Registro.ConfirmacaoConsumoEnum.sim, resultado.getConfirmacaoConsumo());
    }

    @Test
    void deveLancarExcecaoQuandoRegistroNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> registroService.buscarPorId(999));
    }

}

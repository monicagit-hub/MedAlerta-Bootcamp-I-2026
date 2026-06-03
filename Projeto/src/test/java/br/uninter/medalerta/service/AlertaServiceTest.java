package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.AlertaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertaServiceTest {

    @Mock
    private AlertaRepository repository;

    @Mock
    private HorarioService horarioService;

    @Mock
    private RegistroRepository registroRepository;

    @InjectMocks
    private AlertaService alertaService;

    private Alerta alertaFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        return new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
    }

    @Test
    void deveListarTodosOsAlertas() {
        when(repository.findByHorario_UsuarioMedicamento_Usuario_IdUsuario(1))
                .thenReturn(List.of(alertaFake()));

        List<Alerta> resultado = alertaService.listarPorUsuario(1);

        assertEquals(1, resultado.size());
        assertEquals(Alerta.StatusAlertaEnum.emitido, resultado.get(0).getStatusAlerta());
    }

    @Test
    void deveBuscarAlertaPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(alertaFake()));

        Alerta resultado = alertaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(Alerta.StatusAlertaEnum.emitido, resultado.getStatusAlerta());
    }

    @Test
    void deveLancarExcecaoQuandoAlertaNaoEncontrado() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alertaService.buscarPorId(999));
    }

    @Test
    void deveConfirmarAlertaECriarRegistro() {
        Alerta alerta = alertaFake();
        when(repository.findById(1)).thenReturn(Optional.of(alerta));
        when(repository.save(any(Alerta.class))).thenReturn(alerta);
        when(registroRepository.save(any(Registro.class))).thenReturn(new Registro());

        Alerta resultado = alertaService.confirmar(1);

        assertEquals(Alerta.StatusAlertaEnum.confirmado, resultado.getStatusAlerta());
        verify(registroRepository).save(argThat(r ->
                r.getConfirmacaoConsumo() == Registro.ConfirmacaoConsumoEnum.sim));
    }

    @Test
    void deveCancelarAlertaECriarRegistro() {
        Alerta alerta = alertaFake();
        when(repository.findById(1)).thenReturn(Optional.of(alerta));
        when(repository.save(any(Alerta.class))).thenReturn(alerta);
        when(registroRepository.save(any(Registro.class))).thenReturn(new Registro());

        Alerta resultado = alertaService.cancelar(1);

        assertEquals(Alerta.StatusAlertaEnum.cancelado, resultado.getStatusAlerta());
        verify(registroRepository).save(argThat(r ->
                r.getConfirmacaoConsumo() == Registro.ConfirmacaoConsumoEnum.nao));
    }

}

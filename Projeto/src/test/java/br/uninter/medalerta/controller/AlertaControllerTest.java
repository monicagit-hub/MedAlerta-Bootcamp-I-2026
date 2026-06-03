package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.security.JwtUtil;
import br.uninter.medalerta.security.UsuarioDetalhes;
import br.uninter.medalerta.security.UsuarioDetalhesService;
import br.uninter.medalerta.service.AlertaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertaController.class)
public class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertaService alertaService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UsuarioDetalhesService usuarioDetalhesService;

    private UsernamePasswordAuthenticationToken autenticacaoFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        UsuarioDetalhes detalhes = new UsuarioDetalhes(usuario);
        return new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
    }

    private Alerta alertaFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        return new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
    }

    @Test
    void deveListarAlertasDoUsuario() throws Exception {
        when(alertaService.listarPorUsuario(any())).thenReturn(List.of(alertaFake()));

        mockMvc.perform(get("/alertas").with(authentication(autenticacaoFake())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].statusAlerta").value("emitido"));
    }

    @Test
    void deveRetornar404QuandoAlertaNaoEncontrado() throws Exception {
        when(alertaService.buscarPorId(999))
                .thenThrow(new RuntimeException("Alerta não encontrado! ID: 999"));

        mockMvc.perform(get("/alertas/999").with(authentication(autenticacaoFake())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveConfirmarAlerta() throws Exception {
        Alerta confirmado = alertaFake();
        confirmado.setStatusAlerta(Alerta.StatusAlertaEnum.confirmado);
        when(alertaService.confirmar(1)).thenReturn(confirmado);

        mockMvc.perform(patch("/alertas/1/confirmar")
                .with(authentication(autenticacaoFake()))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusAlerta").value("confirmado"));
    }

    @Test
    void deveCancelarAlerta() throws Exception {
        Alerta cancelado = alertaFake();
        cancelado.setStatusAlerta(Alerta.StatusAlertaEnum.cancelado);
        when(alertaService.cancelar(1)).thenReturn(cancelado);

        mockMvc.perform(patch("/alertas/1/cancelar")
                .with(authentication(autenticacaoFake()))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusAlerta").value("cancelado"));
    }

}

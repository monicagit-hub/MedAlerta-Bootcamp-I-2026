package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.security.UsuarioDetalhes;
import br.uninter.medalerta.service.RegistroService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroController.class)
public class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistroService registroService;

    private UsernamePasswordAuthenticationToken autenticacaoFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        UsuarioDetalhes detalhes = new UsuarioDetalhes(usuario);
        return new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
    }

    @Test
    void deveListarRegistrosDoUsuario() throws Exception {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        Alerta alerta = new Alerta(horario, LocalDateTime.of(2026, 4, 15, 8, 0), Alerta.StatusAlertaEnum.emitido);
        Registro registro = new Registro(alerta, Registro.ConfirmacaoConsumoEnum.sim);
        when(registroService.listarPorUsuario(any())).thenReturn(List.of(registro));

        mockMvc.perform(get("/registros").with(authentication(autenticacaoFake())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].confirmacaoConsumo").value("sim"));
    }

    @Test
    void deveRetornar404QuandoRegistroNaoEncontrado() throws Exception {
        when(registroService.buscarPorId(999))
                .thenThrow(new RuntimeException("Registro não encontrado! ID: 999"));

        mockMvc.perform(get("/registros/999").with(authentication(autenticacaoFake())))
                .andExpect(status().isNotFound());
    }

}

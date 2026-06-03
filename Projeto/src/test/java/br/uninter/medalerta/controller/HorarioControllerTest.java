package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.security.UsuarioDetalhes;
import br.uninter.medalerta.service.HorarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HorarioController.class)
public class HorarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorarioService horarioService;

    private UsernamePasswordAuthenticationToken autenticacaoFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        UsuarioDetalhes detalhes = new UsuarioDetalhes(usuario);
        return new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
    }

    @Test
    void deveListarHorariosDoUsuario() throws Exception {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        Horario horario = new Horario(vinculo, LocalTime.of(8, 0), "8 em 8 horas");
        when(horarioService.listarPorUsuario(any())).thenReturn(List.of(horario));

        mockMvc.perform(get("/horarios").with(authentication(autenticacaoFake())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].horarioUso").value("08:00:00"));
    }

    @Test
    void deveRetornar404QuandoHorarioNaoEncontrado() throws Exception {
        when(horarioService.buscarPorId(999))
                .thenThrow(new RuntimeException("Horário não encontrado! ID: 999"));

        mockMvc.perform(get("/horarios/999").with(authentication(autenticacaoFake())))
                .andExpect(status().isNotFound());
    }

}


package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.security.JwtUtil;
import br.uninter.medalerta.security.UsuarioDetalhes;
import br.uninter.medalerta.security.UsuarioDetalhesService;
import br.uninter.medalerta.service.MedicamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicamentoController.class)
public class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UsuarioDetalhesService usuarioDetalhesService;

    @MockBean
    private MedicamentoService medicamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsernamePasswordAuthenticationToken autenticacaoFake() {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        UsuarioDetalhes detalhes = new UsuarioDetalhes(usuario);
        return new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
    }

    @Test
    void deveListarTodosOsMedicamentos() throws Exception {
        Medicamento medicamento = new Medicamento("Rivotril", "Clonazepam");
        when(medicamentoService.listarTodos()).thenReturn(List.of(medicamento));

        mockMvc.perform(get("/medicamentos").with(authentication(autenticacaoFake())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeComercial").value("Rivotril"));
    }

    @Test
    void deveCadastrarMedicamento() throws Exception {
        Medicamento medicamento = new Medicamento("Rivotril", "Clonazepam");
        when(medicamentoService.salvar(any(Medicamento.class))).thenReturn(medicamento);

        mockMvc.perform(post("/medicamentos")
                .with(authentication(autenticacaoFake()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeComercial").value("Rivotril"));
    }

    @Test
    void deveRetornar404QuandoMedicamentoNaoEncontrado() throws Exception {
        when(medicamentoService.buscarPorId(999))
                .thenThrow(new RuntimeException("Medicamento não encontrado"));

        mockMvc.perform(get("/medicamentos/999").with(authentication(autenticacaoFake())))
                .andExpect(status().isNotFound());
    }

}

package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarTodosOsUsuarios() throws Exception {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        when(usuarioService.listarTodos()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"));
    }

    @Test
    void deveCadastrarUsuario() throws Exception {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ana"));
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        when(usuarioService.buscarPorId(999))
                .thenThrow(new RuntimeException("Usuário não encontrado! ID: 999"));

        mockMvc.perform(get("/usuarios/999"))
                .andExpect(status().isNotFound());
    }

}
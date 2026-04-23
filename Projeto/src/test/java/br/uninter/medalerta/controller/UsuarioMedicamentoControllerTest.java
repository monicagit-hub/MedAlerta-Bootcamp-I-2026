package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.service.UsuarioMedicamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioMedicamentoController.class)
public class UsuarioMedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioMedicamentoService usuarioMedicamentoService;

    @Test
    void deveListarTodosOsVinculos() throws Exception {
        Usuario usuario = new Usuario("Ana", "41999990001", "ana@email.com");
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        UsuarioMedicamento vinculo = new UsuarioMedicamento(usuario, medicamento, "1 comprimido");
        when(usuarioMedicamentoService.listarTodos()).thenReturn(List.of(vinculo));

        mockMvc.perform(get("/vinculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dosagem").value("1 comprimido"));
    }

    @Test
    void deveRetornar404QuandoVinculoNaoEncontrado() throws Exception {
        when(usuarioMedicamentoService.buscarPorId(999))
                .thenThrow(new RuntimeException("Vínculo não encontrado! ID: 999"));

        mockMvc.perform(get("/vinculos/999"))
                .andExpect(status().isNotFound());
    }

}
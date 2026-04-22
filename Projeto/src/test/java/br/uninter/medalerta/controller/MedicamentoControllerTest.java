package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.service.MedicamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicamentoController.class)
public class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicamentoService medicamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarTodosOsMedicamentos() throws Exception {
        Medicamento medicamento = new Medicamento("Rivotril", "Clonazepam");
        when(medicamentoService.listarTodos()).thenReturn(List.of(medicamento));

        mockMvc.perform(get("/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeComercial").value("Rivotril"));
    }

    @Test
    void deveCadastrarMedicamento() throws Exception {
       Medicamento medicamento = new Medicamento("Rivotril", "Clonazepam");
        when(medicamentoService.salvar(any(Medicamento.class))).thenReturn(medicamento);

        mockMvc.perform(post("/medicamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeComercial").value("Rivotril"));
    }

    @Test
    void deveRetornar404QuandoMedicamentoNaoEncontrado() throws Exception {
        when(medicamentoService.buscarPorId(999))
                .thenThrow(new RuntimeException("Medicamento não encontrado"));

                mockMvc.perform(get("/medicamentos/999"))
                .andExpect(status().isNotFound());
    }

}
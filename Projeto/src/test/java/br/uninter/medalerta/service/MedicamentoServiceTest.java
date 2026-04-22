package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.repository.MedicamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository repository;

    @InjectMocks
    private MedicamentoService medicamentoService;

    @Test
    void deveListarTodosOsMedicamentos() {
        // Arrange
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        when(repository.findAll()).thenReturn(List.of(medicamento));

        // Act
        List<Medicamento> resultado = medicamentoService.listarTodos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Tylenol", resultado.get(0).getNomeComercial());
    }

    @Test
    void deveBuscarMedicamentoPorId() {
        // Arrange
        Medicamento medicamento = new Medicamento("Tylenol", "Paracetamol");
        when(repository.findById(1)).thenReturn(Optional.of(medicamento));

        // Act
        Medicamento resultado = medicamentoService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Tylenol", resultado.getNomeComercial());
    }
    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoEncontrado() {
        // Arrange
        when(repository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> medicamentoService.buscarPorId(999));
    }

}
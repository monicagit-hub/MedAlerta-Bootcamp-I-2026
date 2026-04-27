package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.service.MedicamentoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public List<Medicamento> listarTodos() {
        return medicamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Medicamento> salvar(@Valid @RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(medicamentoService.salvar(medicamento));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizar(@PathVariable Integer id, @RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(medicamentoService.atualizar(id, medicamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        medicamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
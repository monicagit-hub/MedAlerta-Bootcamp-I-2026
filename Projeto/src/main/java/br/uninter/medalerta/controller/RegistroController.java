package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public List<Registro> listarTodos() {
        return registroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(registroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Registro> salvar(
            @RequestParam Integer idAlerta,
            @RequestParam String dataHorarioConsumo,
            @RequestParam Registro.ConfirmacaoConsumoEnum confirmacaoConsumo) {
        return ResponseEntity.ok(
            registroService.salvar(idAlerta, LocalDateTime.parse(dataHorarioConsumo), confirmacaoConsumo)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        registroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
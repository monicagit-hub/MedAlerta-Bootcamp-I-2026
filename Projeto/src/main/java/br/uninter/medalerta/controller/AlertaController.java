package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public List<Alerta> listarTodos() {
        return alertaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Alerta> salvar(
            @RequestParam Integer idHorario,
            @RequestParam String dataHorarioAlerta,
            @RequestParam Alerta.StatusAlertaEnum statusAlerta) {
        return ResponseEntity.ok(
            alertaService.salvar(idHorario, LocalDateTime.parse(dataHorarioAlerta), statusAlerta)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
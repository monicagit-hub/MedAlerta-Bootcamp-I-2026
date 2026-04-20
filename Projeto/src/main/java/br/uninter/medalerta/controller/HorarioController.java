package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public List<Horario> listarTodos() {
        return horarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Horario> salvar(
            @RequestParam Integer idUsuarioMedicamento,
            @RequestParam String horarioUso,
            @RequestParam String frequenciaUso) {
        return ResponseEntity.ok(
            horarioService.salvar(idUsuarioMedicamento, LocalTime.parse(horarioUso), frequenciaUso)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        horarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
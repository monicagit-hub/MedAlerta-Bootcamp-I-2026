package br.uninter.medalerta.controller;

import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.security.UsuarioDetalhes;
import br.uninter.medalerta.service.UsuarioMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinculos")
public class UsuarioMedicamentoController {

    @Autowired
    private UsuarioMedicamentoService usuarioMedicamentoService;

    @GetMapping
    public List<UsuarioMedicamento> listar(@AuthenticationPrincipal UsuarioDetalhes usuarioLogado) {
        return usuarioMedicamentoService.listarPorUsuario(usuarioLogado.getUsuario().getIdUsuario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioMedicamento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioMedicamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioMedicamento> salvar( 
            @RequestParam Integer idUsuario,
            @RequestParam Integer idMedicamento,
            @RequestParam String dosagem,
            @RequestParam String formaUso) {
        return ResponseEntity.ok(
            usuarioMedicamentoService.salvar(idUsuario, idMedicamento, dosagem, formaUso)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioMedicamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
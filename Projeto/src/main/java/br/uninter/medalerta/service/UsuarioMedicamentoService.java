package br.uninter.medalerta.service;

import br.uninter.medalerta.model.UsuarioMedicamento;
import br.uninter.medalerta.repository.UsuarioMedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioMedicamentoService {

    @Autowired
    private UsuarioMedicamentoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicamentoService medicamentoService;

    public List<UsuarioMedicamento> listarTodos() {
        return repository.findAll();
    }

    public UsuarioMedicamento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado! ID: " + id));
    }

    public UsuarioMedicamento salvar(Integer idUsuario, Integer idMedicamento, String dosagem, String formaUso) {
        UsuarioMedicamento vinculo = new UsuarioMedicamento();
        vinculo.setUsuario(usuarioService.buscarPorId(idUsuario));
        vinculo.setMedicamento(medicamentoService.buscarPorId(idMedicamento));
        vinculo.setDosagem(dosagem);
        vinculo.setFormaUso(formaUso);
        return repository.save(vinculo);
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
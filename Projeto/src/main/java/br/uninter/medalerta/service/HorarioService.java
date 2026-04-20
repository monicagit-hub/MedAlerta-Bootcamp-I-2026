package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository repository;

    @Autowired
    private UsuarioMedicamentoService usuarioMedicamentoService;

    public List<Horario> listarTodos() {
        return repository.findAll();
    }

    public Horario buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado! ID: " + id));
    }

    public Horario salvar(Integer idUsuarioMedicamento, LocalTime horarioUso, String frequenciaUso) {
        Horario horario = new Horario();
        horario.setUsuarioMedicamento(usuarioMedicamentoService.buscarPorId(idUsuarioMedicamento));
        horario.setHorarioUso(horarioUso);
        horario.setFrequenciaUso(frequenciaUso);
        return repository.save(horario);
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
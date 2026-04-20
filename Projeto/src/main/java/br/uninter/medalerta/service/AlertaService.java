package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    @Autowired
    private HorarioService horarioService;

    public List<Alerta> listarTodos() {
        return repository.findAll();
    }

    public Alerta buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado! ID: " + id));
    }

    public Alerta salvar(Integer idHorario, LocalDateTime dataHorarioAlerta, Alerta.StatusAlertaEnum statusAlerta) {
        Alerta alerta = new Alerta();
        alerta.setHorario(horarioService.buscarPorId(idHorario));
        alerta.setDataHorarioAlerta(dataHorarioAlerta);
        alerta.setStatusAlerta(statusAlerta);
        return repository.save(alerta);
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
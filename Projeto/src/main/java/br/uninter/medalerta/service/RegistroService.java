package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository repository;

    @Autowired
    private AlertaService alertaService;

    public List<Registro> listarTodos() {
        return repository.findAll();
    }

    public Registro buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado! ID: " + id));
    }

    public Registro salvar(Integer idAlerta, LocalDateTime dataHorarioConsumo, Registro.ConfirmacaoConsumoEnum confirmacaoConsumo) {
        Registro registro = new Registro();
        registro.setAlerta(alertaService.buscarPorId(idAlerta));
        registro.setDataHorarioConsumo(dataHorarioConsumo);
        registro.setConfirmacaoConsumo(confirmacaoConsumo);
        return repository.save(registro);
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
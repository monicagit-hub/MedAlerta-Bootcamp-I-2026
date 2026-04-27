package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository repository;

    public List<Medicamento> listarTodos() {
        return repository.findAll();
    }

    public Medicamento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado! ID: " + id));
    }

    public Medicamento salvar(Medicamento medicamento) {
        return repository.save(medicamento);
    }

    public Medicamento atualizar(Integer id, Medicamento medicamento) {
        Medicamento existente = buscarPorId(id);
        existente.setNomeComercial(medicamento.getNomeComercial());
        existente.setNomeGenerico(medicamento.getNomeGenerico());
        existente.setFormaUso(medicamento.getFormaUso());
        existente.setObservacao(medicamento.getObservacao());
        existente.setQuantidade(medicamento.getQuantidade());
        return repository.save(existente);
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
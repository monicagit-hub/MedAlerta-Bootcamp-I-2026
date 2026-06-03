package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Registro;
import br.uninter.medalerta.repository.AlertaRepository;
import br.uninter.medalerta.repository.RegistroRepository;
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

    @Autowired
    private RegistroRepository registroRepository;

    public List<Alerta> listarPorUsuario(Integer idUsuario) {
        return repository.findByHorario_UsuarioMedicamento_Usuario_IdUsuario(idUsuario);
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

    public Alerta confirmar(Integer id) {
        Alerta alerta = buscarPorId(id);
        alerta.setStatusAlerta(Alerta.StatusAlertaEnum.confirmado);
        repository.save(alerta);

        Registro registro = new Registro(alerta, Registro.ConfirmacaoConsumoEnum.sim);
        registro.setDataHorarioConsumo(LocalDateTime.now());
        registroRepository.save(registro);

        return alerta;
    }

    public Alerta cancelar(Integer id) {
        Alerta alerta = buscarPorId(id);
        alerta.setStatusAlerta(Alerta.StatusAlertaEnum.cancelado);
        repository.save(alerta);

        Registro registro = new Registro(alerta, Registro.ConfirmacaoConsumoEnum.nao);
        registro.setDataHorarioConsumo(LocalDateTime.now());
        registroRepository.save(registro);

        return alerta;
    }

    public void deletar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

}
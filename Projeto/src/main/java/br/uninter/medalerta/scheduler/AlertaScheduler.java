package br.uninter.medalerta.scheduler;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;
import br.uninter.medalerta.repository.AlertaRepository;
import br.uninter.medalerta.repository.HorarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AlertaScheduler {

    private static final Logger log = LoggerFactory.getLogger(AlertaScheduler.class);

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    // Roda a cada minuto
    @Scheduled(fixedRate = 60000)
    public void gerarAlertasDoDia() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = inicioDia.plusDays(1).minusSeconds(1);

        List<Horario> horarios = horarioRepository.findAll();

        for (Horario horario : horarios) {
            boolean jaExiste = alertaRepository.existsByHorarioAndDataHorarioAlertaBetween(
                    horario, inicioDia, fimDia);

            if (!jaExiste) {
                LocalDateTime dataHorarioAlerta = LocalDate.now()
                        .atTime(horario.getHorarioUso());

                Alerta alerta = new Alerta(horario, dataHorarioAlerta, Alerta.StatusAlertaEnum.emitido);
                alertaRepository.save(alerta);

                log.info("Alerta criado para horário {} — {}",
                        horario.getIdHorario(), dataHorarioAlerta);
            }
        }
    }
}

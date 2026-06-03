package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.Horario;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByHorario_UsuarioMedicamento_Usuario_IdUsuario(Integer idUsuario);

    boolean existsByHorarioAndDataHorarioAlertaBetween(Horario horario, LocalDateTime inicio, LocalDateTime fim);
}
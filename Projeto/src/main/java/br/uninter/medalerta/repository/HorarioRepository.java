package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Horario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    List<Horario> findByUsuarioMedicamento_Usuario_IdUsuario(Integer idUsuario);

}
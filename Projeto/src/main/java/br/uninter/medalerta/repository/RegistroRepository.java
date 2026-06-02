package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Registro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    List<Registro> findByAlerta_Horario_UsuarioMedicamento_Usuario_IdUsuario(Integer idUsuario);

}
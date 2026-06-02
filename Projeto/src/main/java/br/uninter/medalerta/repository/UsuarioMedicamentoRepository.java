package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.UsuarioMedicamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMedicamentoRepository extends JpaRepository<UsuarioMedicamento, Integer> {
    List<UsuarioMedicamento> findByUsuario_IdUsuario(Integer idUsuario);

}
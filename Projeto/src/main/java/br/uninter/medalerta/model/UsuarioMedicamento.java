package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "UsuarioMedicamento")
public class UsuarioMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuarioMedicamento;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idMedicamento", nullable = false)
    private Medicamento medicamento;

    @NotBlank(message = "Dosagem é obrigatório")
    @Column(nullable = false, length = 50)
    private String dosagem;

    @NotBlank(message = "Forma de uso é obrigatório")
    @Column(nullable = false, length = 100)
     private String formaUso;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioMedicamento", cascade = CascadeType.ALL)
    private List<Horario> horarios;

    public UsuarioMedicamento() {}

        public UsuarioMedicamento(Usuario usuario, Medicamento medicamento, String dosagem) {
            this.usuario = usuario;
            this.medicamento = medicamento;
            this.dosagem = dosagem;
        }


    public Integer getIdUsuarioMedicamento() { return idUsuarioMedicamento; }
    public void setIdUsuarioMedicamento(Integer idUsuarioMedicamento) { this.idUsuarioMedicamento = idUsuarioMedicamento; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }

    public String getFormaUso() { return formaUso; }
    public void setFormaUso(String formaUso) { this.formaUso = formaUso; }

    public List<Horario> getHorarios() { return horarios; }
    public void setHorarios(List<Horario> horarios) { this.horarios = horarios; }


    @Override
        public String toString() {
        return "UsuarioMedicamento{" +
            "id=" + idUsuarioMedicamento +
            ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
            ", medicamento=" + (medicamento != null ? medicamento.getNomeComercial() : "null") +
            ", dosagem='" + dosagem + '\'' +
            '}';
        }
}
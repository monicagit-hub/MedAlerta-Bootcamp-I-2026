package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "idUsuarioMedicamento", nullable = false)
    private UsuarioMedicamento usuarioMedicamento;

    @Column(nullable = false)
    private LocalTime horarioUso;

    @Column(length = 50)
    private String frequenciaUso;

    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL)
    private List<Alerta> alertas;

        public Horario() {}

            public Horario(UsuarioMedicamento usuarioMedicamento, LocalTime horarioUso, String frequenciaUso) {
                this.usuarioMedicamento = usuarioMedicamento;
                this.horarioUso = horarioUso;
                this.frequenciaUso = frequenciaUso;
            }

    public Integer getIdHorario() { return idHorario; }
    public void setIdHorario(Integer idHorario) { this.idHorario = idHorario; }

    public UsuarioMedicamento getUsuarioMedicamento() { return usuarioMedicamento; }
    public void setUsuarioMedicamento(UsuarioMedicamento usuarioMedicamento) { this.usuarioMedicamento = usuarioMedicamento; }

    public LocalTime getHorarioUso() { return horarioUso; }
    public void setHorarioUso(LocalTime horarioUso) { this.horarioUso = horarioUso; }

    public String getFrequenciaUso() { return frequenciaUso; }
    public void setFrequenciaUso(String frequenciaUso) { this.frequenciaUso = frequenciaUso; }

    public List<Alerta> getAlertas() { return alertas; }
    public void setAlertas(List<Alerta> alertas) { this.alertas = alertas; }


    @Override
    public String toString() {
    return "Horario{" +
            "id=" + idHorario +
            ", horarioUso=" + horarioUso +
            ", frequenciaUso='" + frequenciaUso + '\'' +
            '}';
    }

}
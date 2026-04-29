package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlerta;

    @ManyToOne
    @JoinColumn(name = "idHorario", nullable = false)
    private Horario horario;

    @NotNull(message = "data horario alerta é obrigatório")
    @Column(nullable = false)
     private LocalDateTime dataHorarioAlerta;

    @NotNull(message = "Status alerta é obrigatório")
    @Column(nullable = false)
    private StatusAlertaEnum statusAlerta;

    public enum StatusAlertaEnum {
    emitido, confirmado, cancelado, nao_emitido
}

    @OneToOne(mappedBy = "alerta", cascade = CascadeType.ALL)
    private Registro registro;


    public Alerta() {}

        public Alerta(Horario horario, LocalDateTime dataHorarioAlerta, StatusAlertaEnum statusAlerta) {
        this.horario = horario;
        this.dataHorarioAlerta = dataHorarioAlerta;
        this.statusAlerta = statusAlerta;
    }

    public Integer getIdAlerta() { return idAlerta; }
    public void setIdAlerta(Integer idAlerta) { this.idAlerta = idAlerta; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public LocalDateTime getDataHorarioAlerta() { return dataHorarioAlerta; }
    public void setDataHorarioAlerta(LocalDateTime dataHorarioAlerta) { this.dataHorarioAlerta = dataHorarioAlerta; }

    public StatusAlertaEnum getStatusAlerta() { return statusAlerta; }
    public void setStatusAlerta(StatusAlertaEnum statusAlerta) { this.statusAlerta = statusAlerta; }

    public Registro getRegistro() { return registro; }
    public void setRegistro(Registro registro) { this.registro = registro; }



        @Override
        public String toString() {
            return "Alerta{" +
                    "id=" + idAlerta +
                    ", dataHorarioAlerta=" + dataHorarioAlerta +
                    ", statusAlerta=" + statusAlerta +
                    '}';
        }
}
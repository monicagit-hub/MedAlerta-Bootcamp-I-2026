package br.uninter.medalerta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistro;

    @OneToOne
    @JoinColumn(name = "idAlerta", nullable = false)
    private Alerta alerta;

    private LocalDateTime dataHorarioConsumo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfirmacaoConsumoEnum confirmacaoConsumo;


    public enum ConfirmacaoConsumoEnum {
        sim, nao
    }

    public Registro() {}

    public Registro(Alerta alerta, ConfirmacaoConsumoEnum confirmacaoConsumo) {
        this.alerta = alerta;
        this.confirmacaoConsumo = confirmacaoConsumo;
    }

    public Integer getIdRegistro() { return idRegistro; }
    public void setIdRegistro(Integer idRegistro) { this.idRegistro = idRegistro; }

    public Alerta getAlerta() { return alerta; }
    public void setAlerta(Alerta alerta) { this.alerta = alerta; }

    public LocalDateTime getDataHorarioConsumo() { return dataHorarioConsumo; }
    public void setDataHorarioConsumo(LocalDateTime dataHorarioConsumo) { this.dataHorarioConsumo = dataHorarioConsumo; }

    public ConfirmacaoConsumoEnum getConfirmacaoConsumo() { return confirmacaoConsumo; }
    public void setConfirmacaoConsumo(ConfirmacaoConsumoEnum confirmacaoConsumo) { this.confirmacaoConsumo = confirmacaoConsumo; }

    @Override
    public String toString() {
        return "Registro{" +
                "id=" + idRegistro +
                ", dataHorarioConsumo=" + dataHorarioConsumo +
                ", confirmacaoConsumo=" + confirmacaoConsumo +
                '}';
    }
}
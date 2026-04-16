package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedicamento;

    @Column(nullable = false, length = 100)
    private String nomeComercial;

    @Column(length = 100)
    private String nomeGenerico;

    @Column(length = 100)
    private String formaUso;

    @Column(length = 200)
    private String observacao;

    @Enumerated(EnumType.STRING)
    private QuantidadeEnum quantidade;

    public enum QuantidadeEnum {
         unidade, ml
    }

    public Medicamento() {}

        public Medicamento(String nomeComercial, String nomeGenerico) {
            this.nomeComercial = nomeComercial;
            this.nomeGenerico = nomeGenerico;
        }


    public Integer getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(Integer idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNomeComercial() { return nomeComercial; }
    public void setNomeComercial(String nomeComercial) { this.nomeComercial = nomeComercial; }

    public String getNomeGenerico() { return nomeGenerico; }
    public void setNomeGenerico(String nomeGenerico) { this.nomeGenerico = nomeGenerico; }

    public String getFormaUso() { return formaUso; }
    public void setFormaUso(String formaUso) { this.formaUso = formaUso; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public QuantidadeEnum getQuantidade() { return quantidade; }
    public void setQuantidade(QuantidadeEnum quantidade) { this.quantidade = quantidade; }

        @Override
        public String toString() {
            return "Medicamento{" +
                    "id=" + idMedicamento +
                    ", nomeComercial='" + nomeComercial + '\'' +
                    ", nomeGenerico='" + nomeGenerico + '\'' +
                    ", quantidade=" + quantidade +
                    '}';
        }
}
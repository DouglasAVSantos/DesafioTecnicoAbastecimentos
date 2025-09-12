package com.desafioTecnicoJunior.Abastecimentos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Abastecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bomba_id")
    private Bomba bomba;
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private BigDecimal litragem;

    public Abastecimento(Bomba bomba, BigDecimal valorTotal, Boolean porValor) {

        this.bomba = bomba;
        this.data = LocalDateTime.now();
        if (porValor) {
            this.valorTotal = valorTotal;
            this.litragem = valorTotal.divide(bomba.getTipo().getValorPorLitro(),6, RoundingMode.HALF_UP);
        }
        if (!porValor) {
            this.valorTotal = valorTotal.multiply(bomba.getTipo().getValorPorLitro());
            this.litragem = valorTotal;
        }

    }

    public Abastecimento(Bomba bomba, BigDecimal valor) {
        this(bomba, valor, true);
    }

}

package com.desafioTecnicoJunior.Abastecimentos.model;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDeCombustivelDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Data
public class TipoDeCombustivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private BigDecimal valorPorLitro;

    public TipoDeCombustivel(TipoDeCombustivelDto dto) {
        this.nome = dto.nome();
        this.valorPorLitro = dto.valorPorLitro();
    }

}

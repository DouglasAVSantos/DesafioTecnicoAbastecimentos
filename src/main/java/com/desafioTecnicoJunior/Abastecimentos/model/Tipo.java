package com.desafioTecnicoJunior.Abastecimentos.model;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    @Column(columnDefinition = "NUMERIC(10,3)")
    private BigDecimal valorPorLitro;

    public Tipo(TipoDto dto) {
        this.nome = dto.nome().toLowerCase();
        this.valorPorLitro = dto.valorPorLitro();
    }

}

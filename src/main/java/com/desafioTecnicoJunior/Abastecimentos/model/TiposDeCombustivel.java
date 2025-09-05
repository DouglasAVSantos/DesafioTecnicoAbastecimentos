package com.desafioTecnicoJunior.Abastecimentos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

import static java.awt.SystemColor.text;

@Entity
@Table
@Data
public class TiposDeCombustivel {

    @Column(unique = true)
    private String nome;
    private BigDecimal valor;
}

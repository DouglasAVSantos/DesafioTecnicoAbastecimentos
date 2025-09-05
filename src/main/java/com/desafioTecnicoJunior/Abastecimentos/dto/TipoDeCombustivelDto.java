package com.desafioTecnicoJunior.Abastecimentos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TipoDeCombustivelDto(
        @NotBlank(message = "o nome é obrigatório")
        String nome,
        @NotNull @DecimalMin(value = "0.001", message = "esse campo deve ter valor superior a 0")
        BigDecimal valorPorLitro
) {
}

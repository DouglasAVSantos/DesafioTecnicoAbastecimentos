package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TipoDto(
        @NotBlank(message = "o nome é obrigatório")
        String nome,
        @NotNull(message = "o valorPorLitro é obrigatório")
        @DecimalMin(value = "0.001", message = "esse campo deve ter valor superior a 0")
        @Digits(integer = 10, fraction = 3, message = "Valor inválido, o valor deve ser numerico com 3 casas decimais")
        BigDecimal valorPorLitro
) {
    public TipoDto(Tipo combustivel) {

        this(combustivel.getNome().toLowerCase(),
                combustivel.getValorPorLitro());
    }
}

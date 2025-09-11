package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AbastecimentoDto(
        @NotBlank(message = "o campo 'bomba' é obrigatório")
        String bomba,
        @NotNull(message = "o campo 'valor' é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que 0.01")
        BigDecimal valor
) {
    public AbastecimentoDto(Abastecimento abastecimento){
        this(abastecimento.getBomba().getNome(),abastecimento.getValorTotal());
    }

}

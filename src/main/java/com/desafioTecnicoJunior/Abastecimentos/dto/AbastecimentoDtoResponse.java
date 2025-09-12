package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AbastecimentoDtoResponse(

        Long id,
        String bomba,
        LocalDateTime data,
        BigDecimal valorTotal,
        BigDecimal litragem

) {
    public AbastecimentoDtoResponse(Abastecimento abastecimento) {
        this(abastecimento.getId(),
                abastecimento.getBomba().getNome(),
                abastecimento.getData(),
                abastecimento.getValorTotal(),
                abastecimento.getLitragem());
    }
}

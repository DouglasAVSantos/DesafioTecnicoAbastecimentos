package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AbastecimentoDtoResponse(

        Long id,
        Bomba bomba,
        LocalDateTime data,
        BigDecimal valorTotal,
        BigDecimal litragem

) {
    public AbastecimentoDtoResponse(Abastecimento abastecimento) {
        this(abastecimento.getId(),
                new Bomba(abastecimento.getBomba().getNome(),
                        new Tipo(abastecimento.getBomba().getTipo().getNome(),
                                abastecimento.getBomba().getTipo().getValorPorLitro())),
                abastecimento.getData(),
                abastecimento.getValorTotal(),
                abastecimento.getLitragem());
    }

    @Getter
    @AllArgsConstructor
    public static class Bomba {
        String nome;
        Tipo tipo;

    }

    @Getter
    @AllArgsConstructor
    public static class Tipo {
        String nome;
        BigDecimal valor;
    }

}

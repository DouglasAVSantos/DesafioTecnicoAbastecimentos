package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import jakarta.validation.constraints.NotBlank;

public record BombaDto(
        @NotBlank(message = "campo 'nome' obrigatório.")
        String nome,
        @NotBlank(message = "campo 'tipo' obrigatório")
        String tipo
) {
    public BombaDto(Bomba bomba) {
        this(bomba.getNome(), bomba.getTipo().getNome());
    }
}

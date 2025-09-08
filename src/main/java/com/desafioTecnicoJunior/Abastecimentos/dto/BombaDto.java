package com.desafioTecnicoJunior.Abastecimentos.dto;

import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import jakarta.validation.constraints.NotBlank;

public record BombaDto(
       @NotBlank(message = "campo 'nome' obrigatório.")
        String nome,
       @NotBlank(message = "campo 'tipo' obrigatório")
       Tipo tipo
) {
    public BombaDto(Bomba bomba) {
        this(bomba.getNome(),bomba.getTipo());
    }
}

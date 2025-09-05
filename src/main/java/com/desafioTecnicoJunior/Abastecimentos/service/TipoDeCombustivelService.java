package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDeCombustivelDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.TipoDeCombustivel;
import com.desafioTecnicoJunior.Abastecimentos.repository.TipoDeCombustivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoDeCombustivelService {

    private final TipoDeCombustivelRepository repository;

    public TipoDeCombustivel cadastrar(TipoDeCombustivelDto dto) {
        if (jaCadastrado(dto.nome())) {
            throw new RegistroDuplicadoException("Este combustível já está cadastrado");
        }
        return repository.save(new TipoDeCombustivel(dto));
    }

    public Boolean jaCadastrado(String nome) {
        return repository.findByNome(nome).isPresent();
    }
}

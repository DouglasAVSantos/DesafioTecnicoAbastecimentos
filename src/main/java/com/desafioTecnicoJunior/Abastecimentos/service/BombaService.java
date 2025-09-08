package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.repository.BombaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BombaService {
    private final BombaRepository repository;

    public Bomba cadastrar(BombaDto dto){
        if(jaCadastrado(dto)){
            throw new RegistroDuplicadoException("A bomba '"+dto.nome()+"' já está cadastrada");
        }
        return repository.save(new Bomba(dto));
    }

    public Boolean jaCadastrado(BombaDto dto){
        if(repository.findByNome(dto.nome()).isPresent()){
            return true;
        }
        return false;
    }
}

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
    private final TipoService tipoService;

    public Bomba cadastrar(BombaDto dto){
        if(jaCadastrado(dto)){
            throw new RegistroDuplicadoException("A bomba '"+dto.nome()+"' já está cadastrada");
        }
        if(!tipoService.jaCadastrado(dto.tipo().toLowerCase())){
            throw new NotFoundException("O tipo de combustivel não esta cadastrado");
        }
        return repository.save(new Bomba(dto.nome(),tipoService.findByNome(dto.tipo())));
    }

    public Boolean jaCadastrado(BombaDto dto){
        if(repository.findByNome(dto.nome().toLowerCase()).isPresent()){
            return true;
        }
        return false;
    }
}

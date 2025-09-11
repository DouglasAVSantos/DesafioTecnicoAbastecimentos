package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import com.desafioTecnicoJunior.Abastecimentos.repository.BombaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BombaService {
    private final BombaRepository repository;
    private final TipoService tipoService;

    public Bomba cadastrar(BombaDto dto) {
        if (jaCadastrado(dto)) {
            throw new RegistroDuplicadoException("A bomba '" + dto.nome() + "' já está cadastrada");
        }
        if (!tipoService.jaCadastrado(dto.tipo().toLowerCase())) {
            throw new NotFoundException("O tipo de combustivel não esta cadastrado");
        }
        return repository.save(new Bomba(dto.nome().toLowerCase(), tipoService.findByNome(dto.tipo())));
    }


    public Boolean jaCadastrado(BombaDto dto) {
        if (repository.findByNome(dto.nome().toLowerCase()).isPresent()) {
            return true;
        }
        return false;
    }

    public List<BombaDto> getLista() {
        return repository.findAll().stream().map(BombaDto::new).toList();
    }

    public BombaDto findById(Long id) {
        return new BombaDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Bomba não encontrada para o id: " + id)));
    }

    public BombaDto findByNome(String nome) {
        return new BombaDto(repository.findByNome(nome.toLowerCase()).orElseThrow(() -> new NotFoundException("Bomba não encontrada para o nome: " + nome)));
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Bomba não encontrada para o id: " + id);
        }
        repository.deleteById(id);
    }

    public void deleteByNome(String nome) {
        if (!repository.existsByNome(nome.toLowerCase())) {
            throw new NotFoundException("Bomba não encontrada com o nome: " + nome);
        }
        repository.deleteByNome(nome.toLowerCase());
    }

    public BombaDto atualizar(Long id, BombaDto dto) {
        Bomba desatualizado = repository.findById(id).orElseThrow(() -> new NotFoundException("Bomba não encontrada para o id: " + id));
        Tipo tipoValido = tipoService.findByNome(dto.tipo().toLowerCase());

        desatualizado.setNome(dto.nome().toLowerCase());
        desatualizado.setTipo(tipoValido);

        return new BombaDto(repository.save(desatualizado));

    }
}

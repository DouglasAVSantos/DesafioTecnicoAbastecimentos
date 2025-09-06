package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDeCombustivelDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.TipoDeCombustivel;
import com.desafioTecnicoJunior.Abastecimentos.repository.TipoDeCombustivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDeCombustivelService {

    private final TipoDeCombustivelRepository repository;

    public TipoDeCombustivel cadastrar(TipoDeCombustivelDto dto) {
        if (jaCadastrado(dto.nome().toLowerCase())) {
            throw new RegistroDuplicadoException("Este combustível já está cadastrado");
        }
        return repository.save(new TipoDeCombustivel(dto));
    }

    public Boolean jaCadastrado(String nome) {
        return repository.findByNome(nome).isPresent();
    }

    public TipoDeCombustivel findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + id));
    }

    public TipoDeCombustivel findByNome(String id) {
        return repository.findByNome(id.toLowerCase()).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + id));
    }

    public List<TipoDeCombustivel> getLista() {
        return repository.findAll();
    }

    public String deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + id));
        repository.deleteById(id);
        return "registro de id: "+id+" deletado com sucesso.";
    }

    public String deleteByNome(String nome) {
        repository.findByNome(nome.toLowerCase()).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + nome));
        repository.deleteByNome(nome);
        return "registro de id: '"+nome+"' deletado com sucesso.";
    }

    public TipoDeCombustivelDto atualizar(Long id, TipoDeCombustivelDto dto){
        TipoDeCombustivel desatualizado = this.findById(id);
        desatualizado.setNome(dto.nome().toLowerCase());
        desatualizado.setValorPorLitro(dto.valorPorLitro());
        return new TipoDeCombustivelDto(repository.save(desatualizado));
    }
}

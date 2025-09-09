package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import com.desafioTecnicoJunior.Abastecimentos.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoService {

    private final TipoRepository repository;

    public Tipo cadastrar(TipoDto dto) {
        if (jaCadastrado(dto.nome().toLowerCase())) {
            throw new RegistroDuplicadoException("Este combustível já está cadastrado");
        }
        return repository.save(new Tipo(dto));
    }

    public Boolean jaCadastrado(String nome) {

        if(repository.findByNome(nome.toLowerCase()).isPresent()){
            return true;
        }
        return false;
    }

    public Tipo findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + id));
    }

    public Tipo findByNome(String nome) {
        return repository.findByNome(nome.toLowerCase()).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + nome));
    }

    public List<Tipo> getLista() {
        return repository.findAll();
    }

    public String deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o id: " + id));
        repository.deleteById(id);
        return "registro de id: "+id+" deletado com sucesso.";
    }

    public String deleteByNome(String nome) {
        repository.findByNome(nome.toLowerCase()).orElseThrow(() -> new NotFoundException("Tipo não encontrado para o nome: " + nome));
        repository.deleteByNome(nome);
        return "registro de nome: '"+nome+"' deletado com sucesso.";
    }

    public TipoDto atualizar(Long id, TipoDto dto){
        Tipo desatualizado = this.findById(id);
        desatualizado.setNome(dto.nome().toLowerCase());
        desatualizado.setValorPorLitro(dto.valorPorLitro());
        return new TipoDto(repository.save(desatualizado));
    }
}

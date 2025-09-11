package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.AbastecimentoDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.repository.AbastecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final BombaService bombaService;
    private final AbastecimentoRepository repository;

    public AbastecimentoDto cadastrar(AbastecimentoDto dto){
        Bomba bomba = bombaService.getBomba(dto.bomba());
        return new AbastecimentoDto(repository.save(new Abastecimento(bomba,dto.valor())));
    }

    public List<AbastecimentoDto> getLista(){
        return repository.findAll().stream().map(AbastecimentoDto::new).toList();
    }

    public List<AbastecimentoDto> getLista(String nomeBomba){
        return repository.findAll().stream().filter(c->c.getBomba().getNome().equals(nomeBomba)).map(AbastecimentoDto::new).toList();
    }

    public AbastecimentoDto findById(Long id){
        return new AbastecimentoDto(repository.findById(id).orElseThrow(()-> new NotFoundException("abastecimento não encontrado para o id: '"+id+"'")));
    }

    public void deleteById(Long id){
        if(!repository.existsById(id)){
            throw new NotFoundException("abastecimento não encontrado para o id: '"+id+"'");
        }
        repository.deleteById(id);
    }

    public AbastecimentoDto atualizar(Long id, AbastecimentoDto dto){
      Abastecimento atualizado = repository.findById(id).orElseThrow(()->new NotFoundException("abastecimento não encontrado para o id: '"+id+"'"));
      Bomba bomba = bombaService.getBomba(dto.bomba());
      atualizado.setBomba(bomba);
      atualizado.setData(LocalDateTime.now());
      atualizado.setValorTotal(dto.valor());
      atualizado.setLitragem(dto.valor().divide(bomba.getTipo().getValorPorLitro(),3, RoundingMode.HALF_UP));
      return new AbastecimentoDto(atualizado);
    }

}

package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.AbastecimentoDto;
import com.desafioTecnicoJunior.Abastecimentos.dto.AbastecimentoDtoResponse;
import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.repository.AbastecimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final BombaService bombaService;
    private final AbastecimentoRepository repository;

    public AbastecimentoDtoResponse cadastrar(AbastecimentoDto dto) {
        Bomba bomba = bombaService.getBomba(dto.bomba());
        return new AbastecimentoDtoResponse(repository.save(new Abastecimento(bomba, dto.valor())));
    }

    public List<AbastecimentoDtoResponse> getLista() {
        return repository.findAll().stream().map(AbastecimentoDtoResponse::new).toList();
    }

    public List<AbastecimentoDtoResponse> getLista(String nomeBomba) {
        BombaDto bombaValida = bombaService.findByNome(nomeBomba);
        return repository.findAll().stream().filter(c -> c.getBomba().getNome().equals(bombaValida.nome())).map(AbastecimentoDtoResponse::new).toList();
    }

    public AbastecimentoDtoResponse findById(Long id) {
        return new AbastecimentoDtoResponse(repository.findById(id).orElseThrow(() -> new NotFoundException("abastecimento não encontrado para o id: '" + id + "'")));
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("abastecimento não encontrado para o id: '" + id + "'");
        }
        repository.deleteById(id);
    }

    public AbastecimentoDtoResponse atualizar(Long id, AbastecimentoDto dto) {
        Abastecimento atualizado = repository.findById(id).orElseThrow(() -> new NotFoundException("abastecimento não encontrado para o id: '" + id + "'"));
        Bomba bomba = bombaService.getBomba(dto.bomba());
        atualizado.setBomba(bomba);
        atualizado.setData(LocalDateTime.now());
        atualizado.setValorTotal(dto.valor());
        atualizado.setLitragem(dto.valor().divide(bomba.getTipo().getValorPorLitro(), 6, RoundingMode.HALF_UP));
        return new AbastecimentoDtoResponse(atualizado);
    }

}

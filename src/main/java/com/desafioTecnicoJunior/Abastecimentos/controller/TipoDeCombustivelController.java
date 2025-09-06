package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDeCombustivelDto;
import com.desafioTecnicoJunior.Abastecimentos.model.TipoDeCombustivel;
import com.desafioTecnicoJunior.Abastecimentos.service.TipoDeCombustivelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipo")
public class TipoDeCombustivelController {
    private final TipoDeCombustivelService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TipoDeCombustivelDto> cadastrar(@RequestBody @Valid TipoDeCombustivelDto dto) {
        TipoDeCombustivel combustivel = service.cadastrar(dto);
        return ResponseEntity.created(URI.create("api/v1/tipo/" + combustivel.getId())).body(dto);
    }

    @GetMapping("/{id}")
    ResponseEntity<TipoDeCombustivelDto> buscarPorId(@PathVariable Long id) {
        TipoDeCombustivel combustivel = service.findById(id);
        return ResponseEntity.ok(new TipoDeCombustivelDto(combustivel.getNome(), combustivel.getValorPorLitro()));
    }

    @GetMapping()
    ResponseEntity<TipoDeCombustivelDto> buscarPorNome(@RequestParam(name = "nome") String nome) {
        TipoDeCombustivel combustivel = service.findByNome(nome);
        return ResponseEntity.ok(new TipoDeCombustivelDto(combustivel.getNome(), combustivel.getValorPorLitro()));
    }

    @GetMapping("/")
    ResponseEntity<List<TipoDeCombustivelDto>> buscarTodos() {
        List<TipoDeCombustivel> listaCombustivel = service.getLista();
        return ResponseEntity.ok(listaCombustivel.stream().map(TipoDeCombustivelDto::new).toList());
    }

    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity<Map<String, String>> deletarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("message", service.deleteById(id)));
    }

    @DeleteMapping()
    @Transactional
    ResponseEntity<Map<String, String>> deletarPorNome(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(Map.of("message", service.deleteByNome(nome)));
    }

    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<TipoDeCombustivelDto> atualizar(@PathVariable Long id, @RequestBody @Valid TipoDeCombustivelDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
}

package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDto;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import com.desafioTecnicoJunior.Abastecimentos.service.TipoService;
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
public class TipoController {
    private final TipoService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TipoDto> cadastrar(@RequestBody @Valid TipoDto dto) {
        Tipo combustivel = service.cadastrar(dto);
        return ResponseEntity.created(URI.create("api/v1/tipo/" + combustivel.getId())).body(dto);
    }

    @GetMapping("/{id}")
    ResponseEntity<TipoDto> buscarPorId(@PathVariable Long id) {
        Tipo combustivel = service.findById(id);
        return ResponseEntity.ok(new TipoDto(combustivel.getNome(), combustivel.getValorPorLitro()));
    }

    @GetMapping()
    ResponseEntity<TipoDto> buscarPorNome(@RequestParam(name = "nome") String nome) {
        Tipo combustivel = service.findByNome(nome);
        return ResponseEntity.ok(new TipoDto(combustivel.getNome(), combustivel.getValorPorLitro()));
    }

    @GetMapping("/")
    ResponseEntity<List<TipoDto>> buscarTodos() {
        List<Tipo> listaCombustivel = service.getLista();
        return ResponseEntity.ok(listaCombustivel.stream().map(TipoDto::new).toList());
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
    ResponseEntity<TipoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TipoDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
}

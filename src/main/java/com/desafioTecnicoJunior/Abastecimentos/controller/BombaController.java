package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.service.BombaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/bomba")
@RequiredArgsConstructor
public class BombaController {

    private final BombaService service;

    @PostMapping()
    ResponseEntity<BombaDto> cadastrar(@RequestBody @Valid BombaDto dto) {
        Bomba cadastrada = service.cadastrar(dto);
        return ResponseEntity.created(URI.create("api/v1/bomba/" + cadastrada.getId())).body(new BombaDto(cadastrada));
    }

    @GetMapping("/{id}")
    ResponseEntity<BombaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping()
    ResponseEntity<BombaDto> buscarPorNome(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(service.findByNome(nome));
    }

    @GetMapping("/")
    ResponseEntity<List<BombaDto>> buscarTodos() {
        return ResponseEntity.ok(service.getLista());
    }

    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity<Map<String, String>> deletarPorId(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "registro com id: '" + id + "' deletado com sucesso"));
    }

    @DeleteMapping()
    @Transactional
    ResponseEntity<Map<String, String>> deletarPorNome(@RequestParam(name = "nome") String nome) {
        service.deleteByNome(nome);
        return ResponseEntity.ok(Map.of("message", "registro com nome: '" + nome + "' deletado com sucesso"));
    }

    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<BombaDto> atualizar(@PathVariable Long id, @RequestBody @Valid BombaDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

}

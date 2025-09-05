package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDeCombustivelDto;
import com.desafioTecnicoJunior.Abastecimentos.model.TipoDeCombustivel;
import com.desafioTecnicoJunior.Abastecimentos.service.TipoDeCombustivelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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


}

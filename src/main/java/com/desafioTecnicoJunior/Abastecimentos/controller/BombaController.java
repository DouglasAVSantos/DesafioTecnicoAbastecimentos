package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.service.BombaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/bomba")
@RequiredArgsConstructor
public class BombaController {

    private final BombaService service;

    @PostMapping()
    ResponseEntity<BombaDto>cadastrar(@RequestBody @Valid BombaDto dto){
        Bomba cadastrada = service.cadastrar(dto);
        return ResponseEntity.created(URI.create("api/v1/bomba/"+cadastrada.getId())).body(new BombaDto(cadastrada));
    }

}

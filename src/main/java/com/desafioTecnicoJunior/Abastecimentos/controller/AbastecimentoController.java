package com.desafioTecnicoJunior.Abastecimentos.controller;

import com.desafioTecnicoJunior.Abastecimentos.dto.AbastecimentoDto;
import com.desafioTecnicoJunior.Abastecimentos.dto.AbastecimentoDtoResponse;
import com.desafioTecnicoJunior.Abastecimentos.service.AbastecimentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/abastecimento")
@RequiredArgsConstructor
public class AbastecimentoController {

    private final AbastecimentoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<AbastecimentoDtoResponse>cadastrar(@RequestBody @Valid AbastecimentoDto dto){
        return ResponseEntity.created(URI.create("api/v1/abastecimento/id")).body(service.cadastrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbastecimentoDtoResponse>getAbastecimento(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<AbastecimentoDtoResponse>>getListaAbastecimento(){
        return ResponseEntity.ok(service.getLista());
    }

    @GetMapping("/bomba/")
    public ResponseEntity<List<AbastecimentoDtoResponse>>getListaAbastecimentoDeUmaBomba(@RequestParam String bomba){
        return ResponseEntity.ok(service.getLista(bomba));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AbastecimentoDtoResponse>atualizar(@RequestBody @Valid AbastecimentoDto dto, @PathVariable Long id){
        return ResponseEntity.ok(service.atualizar(id,dto));
    }



}

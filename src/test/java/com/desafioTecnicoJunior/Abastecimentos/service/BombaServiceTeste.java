package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import com.desafioTecnicoJunior.Abastecimentos.repository.BombaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BombaServiceTeste {

    @Mock
    private BombaRepository repository;
    @Mock
    private TipoService tipoService;
    @InjectMocks
    private BombaService bombaService;
    private BombaDto bombaDto;
    private TipoDto tipoDto;
    private Tipo tipo;
    private Bomba bomba;


    @BeforeEach
    void setup() {
        tipoDto = new TipoDto("alcool", new BigDecimal("123.123"));
        bombaDto = new BombaDto("bomba1", "alcool");
        tipo = new Tipo(tipoDto);
        tipo.setId(1l);
        bomba = new Bomba(bombaDto.nome(), tipo);
        bomba.setId(1l);
    }

    @Test
    void deveCadastrarBombaComSucesso() {

        when(repository.save(any(Bomba.class))).thenReturn(bomba);
        when(tipoService.findByNome(tipoDto.nome())).thenReturn(tipo);
        when(tipoService.jaCadastrado(tipoDto.nome())).thenReturn(true);

        Bomba result = bombaService.cadastrar(bombaDto);
        assertEquals(1, result.getId());
        assertEquals("alcool", result.getTipo().getNome());

        verify(repository, times(1)).save(any(Bomba.class));
    }

    @Test
    void deveLancarExcecaoDuplicadaQuandoTentarCadastrar() {
        when(repository.findByNome("bomba1")).thenReturn(Optional.of(bomba));
        RegistroDuplicadoException ex = assertThrows(RegistroDuplicadoException.class, () ->
                bombaService.cadastrar(bombaDto));
        assertEquals("A bomba 'bomba1' já está cadastrada", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoNotFoundQuandoTentarCadastrarBombaSemTipoCriado(){
        when(tipoService.jaCadastrado(bombaDto.tipo())).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class,()->
                bombaService.cadastrar(bombaDto));

        assertEquals("O tipo de combustivel não esta cadastrado",ex.getMessage());
        verify(repository, never()).save(any());
    }
}

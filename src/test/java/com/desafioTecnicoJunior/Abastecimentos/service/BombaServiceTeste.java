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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void deveLancarExcecaoNotFoundQuandoTentarCadastrarBombaSemTipoCriado() {
        when(tipoService.jaCadastrado(bombaDto.tipo())).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                bombaService.cadastrar(bombaDto));

        assertEquals("O tipo de combustivel não esta cadastrado", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveRetornarTrueQuandoJaCadastrado() {
        when(repository.findByNome(bombaDto.nome())).thenReturn(Optional.of(bomba));
        Boolean result = bombaService.jaCadastrado(bombaDto);

        assertEquals(true, result);
        verify(repository, times(1)).findByNome(bomba.getNome());
    }

    @Test
    void deveRetornarFalseQuandoJaCadastrado() {
        when(repository.findByNome(bombaDto.nome())).thenReturn(Optional.empty());
        Boolean result = bombaService.jaCadastrado(bombaDto);

        assertEquals(false, result);
        verify(repository, times(1)).findByNome(bomba.getNome());
    }

    @Test
    void deveRetornarListaComSuceso() {
        List<Bomba> list = List.of(bomba);
        when(repository.findAll()).thenReturn(list);
        List<BombaDto> result = bombaService.getLista();

        assertEquals(1, result.size());
        assertEquals("bomba1", result.get(0).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.of(bomba));
        BombaDto result = bombaService.findById(1l);

        assertEquals("bomba1", result.nome());
        assertEquals("alcool", result.tipo());

        verify(repository, times(1)).findById(1l);
    }

    @Test
    void deveBuscarPorIdSemSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                bombaService.findById(1l));

        assertEquals("Bomba não encontrada para o id: 1", ex.getMessage());
        verify(repository, times(1)).findById(1l);
    }

    @Test
    void deveBuscarPorNomeComSucesso() {
        when(repository.findByNome(bombaDto.nome())).thenReturn(Optional.of(bomba));
        BombaDto result = bombaService.findByNome(bombaDto.nome());

        assertEquals("bomba1", result.nome());
        assertEquals("alcool", result.tipo());

        verify(repository, times(1)).findByNome(bombaDto.nome());
    }

    @Test
    void deveBuscarPorNomeSemSucesso() {
        when(repository.findByNome(bombaDto.nome())).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                bombaService.findByNome(bombaDto.nome()));

        assertEquals("Bomba não encontrada para o nome: bomba1", ex.getMessage());
        verify(repository, times(1)).findByNome(bombaDto.nome());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        assertDoesNotThrow(() -> bombaService.deleteById(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoDeletarPorId() {
        when(repository.existsById(1L)).thenReturn(false);
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                bombaService.deleteById(1L));
        assertEquals("Bomba não encontrada para o id: 1", ex.getMessage());
        verify(repository, never()).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTentarAtualizarComIdInvalido() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () -> bombaService.atualizar(1L, bombaDto));

        assertEquals("Bomba não encontrada para o id: 1", ex.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Bomba.class));
    }

    @Test
    void deveLancarExcecaoQuandoTentarAtualizarComTipoInvalido() {
        when(repository.findById(1L)).thenReturn(Optional.of(bomba));
        when(tipoService.findByNome(bombaDto.tipo())).thenThrow(new NotFoundException("tipo nao encontrado"));
        NotFoundException ex = assertThrows(NotFoundException.class, () -> bombaService.atualizar(1L, bombaDto));
        assertEquals("tipo nao encontrado", ex.getMessage());

        verify(repository, never()).save(any(Bomba.class));
    }

    @Test
    void deveAtualizarComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(bomba));
        when(tipoService.findByNome(bombaDto.tipo())).thenReturn(tipo);
        when(repository.save(any(Bomba.class))).thenReturn(bomba);

        BombaDto result = bombaService.atualizar(1L, bombaDto);

        assertEquals("alcool", result.tipo());
        assertEquals("bomba1", result.nome());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Bomba.class));
        verify(tipoService, times(1)).findByNome(bombaDto.tipo());
    }
}

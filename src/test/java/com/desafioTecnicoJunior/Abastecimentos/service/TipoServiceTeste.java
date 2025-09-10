package com.desafioTecnicoJunior.Abastecimentos.service;

import com.desafioTecnicoJunior.Abastecimentos.dto.TipoDto;
import com.desafioTecnicoJunior.Abastecimentos.exception.NotFoundException;
import com.desafioTecnicoJunior.Abastecimentos.exception.RegistroDuplicadoException;
import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import com.desafioTecnicoJunior.Abastecimentos.repository.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoServiceTeste {

    @InjectMocks
    private TipoService service;
    @Mock
    private TipoRepository repository;
    private TipoDto dto;
    private Tipo tipo;

    @BeforeEach
    void setup() {
        dto = new TipoDto("gasolina", new BigDecimal(123.123));
        tipo = new Tipo(dto);
        tipo.setId(1l);
    }

    @Test
    void deveCadastrarComSucesso() {
        when(repository.save(new Tipo(dto))).thenReturn(tipo);
        Tipo salvo = service.cadastrar(dto);
        assertEquals(1, salvo.getId());
        verify(repository, times(1)).save(any(Tipo.class));
    }

    @Test
    void deveCadastrarSemSucesso() {
        when(repository.findByNome(dto.nome())).thenReturn(Optional.of(tipo));
        RegistroDuplicadoException ex = assertThrows(RegistroDuplicadoException.class, () ->
                service.cadastrar(dto));
        assertEquals("Este combustível já está cadastrado", ex.getMessage());
        verify(repository, never()).save(any(Tipo.class));
    }

    @Test
    void deveRetornarTrueQuandoTipoNaoForCadastrado() {
        when(repository.findByNome(dto.nome())).thenReturn(Optional.of(tipo));
        Boolean result = service.jaCadastrado(tipo.getNome());
        assertEquals(true, result);
        verify(repository).findByNome(dto.nome());
    }

    @Test
    void deveRetornarFalseQuandoTipoNaoForCadastrado() {
        when(repository.findByNome("nome")).thenReturn(Optional.empty());
        Boolean result = service.jaCadastrado("nome");
        assertEquals(false, result);
        verify(repository).findByNome("nome");
    }

    @Test
    void deveBuscarPeloIdComSucesso() {
        when(repository.findById(tipo.getId())).thenReturn(Optional.of(tipo));
        Tipo result = service.findById(tipo.getId());
        assertEquals(1, result.getId());
        assertEquals("gasolina", result.getNome());
        verify(repository).findById(1l);
    }

    @Test
    void deveLancarExQuandoBuscarPeloIdSemSucesso() {
        when(repository.findById(2l)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findById(2l));
        assertEquals("Tipo não encontrado para o id: 2", ex.getMessage());
        verify(repository).findById(2l);
    }

    @Test
    void deveBuscarTipoPeloNomeComSucesso() {
        when(repository.findByNome(dto.nome())).thenReturn(Optional.of(tipo));
        Tipo result = service.findByNome(dto.nome());
        assertEquals(1, result.getId());
        assertEquals("gasolina", result.getNome());
        verify(repository).findByNome(dto.nome());
    }

    @Test
    void deveBuscarTipoPeloNomeSemSucesso() {
        when(repository.findByNome("nome")).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findByNome("nome"));
        assertEquals("Tipo não encontrado para o id: nome", ex.getMessage());
        verify(repository).findByNome("nome");
    }

    @Test
    void deveBuscarListaDeTiposComSucesso() {
        List<Tipo> lista = List.of(new Tipo(dto));
        when(repository.findAll()).thenReturn(lista);
        List<Tipo> result = service.getLista();
        assertEquals(1, result.size());
        assertEquals("gasolina", result.get(0).getNome());
        verify(repository).findAll();
    }

    @Test
    void deveDeletarUmTipoPeloIdComSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.of(tipo));
        String result = service.deleteById(1l);
        assertEquals("registro de id: 1 deletado com sucesso.", result);
        verify(repository, times(1)).deleteById(1l);
    }

    @Test
    void deveDeletarUmTipoPeloIdSemSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                service.deleteById(1l));
        assertEquals("Tipo não encontrado para o id: 1", ex.getMessage());
        verify(repository, never()).deleteById(1l);
    }

    @Test
    void deveDeletarUmTipoPeloNomeComSucesso() {
        when(repository.findByNome("nome")).thenReturn(Optional.of(tipo));
        String result = service.deleteByNome("nome");
        assertEquals("registro de nome: 'nome' deletado com sucesso.", result);
        verify(repository, times(1)).deleteByNome("nome");
    }

    @Test
    void deveDeletarUmTipoPeloNomeSemSucesso() {
        when(repository.findByNome("nome")).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                service.deleteByNome("nome"));
        assertEquals("Tipo não encontrado para o nome: nome", ex.getMessage());
        verify(repository, never()).deleteById(1l);
    }

    @Test
    void deveAtualizarTipoComSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.of(tipo));
        TipoDto dtoParaAtualizar = new TipoDto("alcool", new BigDecimal("321.321"));

        Tipo resultSave = new Tipo(dtoParaAtualizar);
        resultSave.setId(1l);

        when(repository.save(any(Tipo.class))).thenReturn(resultSave);

        TipoDto result = service.atualizar(1l, dtoParaAtualizar);

        assertEquals("alcool", result.nome());
        assertEquals(new BigDecimal("321.321"), result.valorPorLitro());
        verify(repository, times(1)).save(any(Tipo.class));
        verify(repository, times(1)).findById(1l);
    }

    @Test
    void deveAtualizarTipoSemSucesso() {
        when(repository.findById(1l)).thenReturn(Optional.empty());
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                service.atualizar(1l, new TipoDto("none", new BigDecimal("12.12"))));

        assertEquals("Tipo não encontrado para o id: 1", ex.getMessage());
        verify(repository, times(1)).findById(1l);
    }
}

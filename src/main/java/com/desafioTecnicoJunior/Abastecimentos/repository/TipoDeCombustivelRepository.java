package com.desafioTecnicoJunior.Abastecimentos.repository;


import com.desafioTecnicoJunior.Abastecimentos.model.TipoDeCombustivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDeCombustivelRepository extends JpaRepository<TipoDeCombustivel, Long> {

    Optional<TipoDeCombustivel> findByNome(String nome);
}

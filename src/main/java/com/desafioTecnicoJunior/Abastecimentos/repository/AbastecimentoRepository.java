package com.desafioTecnicoJunior.Abastecimentos.repository;

import com.desafioTecnicoJunior.Abastecimentos.model.Abastecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento,Long> {

}

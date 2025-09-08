package com.desafioTecnicoJunior.Abastecimentos.repository;

import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import com.desafioTecnicoJunior.Abastecimentos.model.Bomba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BombaRepository extends JpaRepository<Bomba,Long> {

    public Optional<Bomba> findByNome(String nome);
}

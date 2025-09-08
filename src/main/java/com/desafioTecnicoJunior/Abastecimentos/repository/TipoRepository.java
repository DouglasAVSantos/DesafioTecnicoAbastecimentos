package com.desafioTecnicoJunior.Abastecimentos.repository;


import com.desafioTecnicoJunior.Abastecimentos.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

    Optional<Tipo> findByNome(String nome);
    void deleteByNome(String nome);
}

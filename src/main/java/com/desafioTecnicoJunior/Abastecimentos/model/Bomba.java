package com.desafioTecnicoJunior.Abastecimentos.model;


import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bomba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToOne
    @JoinColumn(name = "id_tipo")
    private Tipo tipo;

    public Bomba(BombaDto dto){
        this.nome = dto.nome().toLowerCase();
        this.tipo = dto.tipo();
    }
}

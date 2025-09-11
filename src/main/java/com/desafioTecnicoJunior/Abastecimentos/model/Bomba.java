package com.desafioTecnicoJunior.Abastecimentos.model;


import com.desafioTecnicoJunior.Abastecimentos.dto.BombaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bomba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private Tipo tipo;

    public Bomba(String nome, Tipo tipo){
        this.nome = nome.toLowerCase();
        tipo.setNome(tipo.getNome().toLowerCase());
        this.tipo = tipo;
    }

}

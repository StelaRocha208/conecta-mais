package com.gdc.gestao_de_contatos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor; // Opcional: se estiver usando Lombok
import lombok.Data; // Opcional
import lombok.NoArgsConstructor; // Opcional

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;

}
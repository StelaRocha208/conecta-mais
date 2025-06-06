package com.gdc.gestao_de_contatos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gdc.gestao_de_contatos.entity.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    // Método para pesquisar contatos pelo nome
    List<Contato> findByNomeContainingIgnoreCase(String nome);

    // Método para pesquisar contatos pelo email
    List<Contato> findByEmailContainingIgnoreCase(String email);

    // Método para pesquisar contatos pelo telefone
    List<Contato> findByTelefoneContainingIgnoreCase(String telefone);
}
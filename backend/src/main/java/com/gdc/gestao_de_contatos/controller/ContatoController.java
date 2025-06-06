package com.gdc.gestao_de_contatos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdc.gestao_de_contatos.entity.Contato;
import com.gdc.gestao_de_contatos.service.ContatoService;

@RestController
@RequestMapping("/api/contatos") // Define o endpoint base para os contatos
public class ContatoController {

    private final ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    // Endpoint para Cadastrar um novo contato
    @PostMapping
    public ResponseEntity<Contato> cadastrarContato(@RequestBody Contato contato) {
        Contato novoContato = contatoService.cadastrarContato(contato);
        return new ResponseEntity<>(novoContato, HttpStatus.CREATED);
    }

    // Endpoint para Listar todos os contatos
    @GetMapping
    public ResponseEntity<List<Contato>> listarTodosContatos() {
        List<Contato> contatos = contatoService.listarTodosContatos();
        return ResponseEntity.ok(contatos);
    }

    // Endpoint para Buscar um contato pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarContatoPorId(@PathVariable Long id) {
        Optional<Contato> contato = contatoService.buscarContatoPorId(id);
        return contato.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para Editar um contato existente
    @PutMapping("/{id}")
    public ResponseEntity<Contato> editarContato(@PathVariable Long id, @RequestBody Contato contatoAtualizado) {
        Optional<Contato> contatoEditado = contatoService.editarContato(id, contatoAtualizado);
        return contatoEditado.map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para Excluir um contato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirContato(@PathVariable Long id) {
        boolean excluido = contatoService.excluirContato(id);
        if (excluido) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // Endpoint para Pesquisar contatos (exemplo genérico)
    @GetMapping("/pesquisar")
    public ResponseEntity<List<Contato>> pesquisarContatos(@RequestParam("termo") String termo) {
        List<Contato> contatosEncontrados = contatoService.pesquisarContatos(termo);
        if (contatosEncontrados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contatosEncontrados);
    }

    // Endpoints de pesquisa mais específicos (opcional)
    @GetMapping("/pesquisar/nome")
    public ResponseEntity<List<Contato>> pesquisarPorNome(@RequestParam("nome") String nome) {
        List<Contato> contatos = contatoService.pesquisarPorNome(nome);
        return contatos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contatos);
    }

    @GetMapping("/pesquisar/email")
    public ResponseEntity<List<Contato>> pesquisarPorEmail(@RequestParam("email") String email) {
        List<Contato> contatos = contatoService.pesquisarPorEmail(email);
        return contatos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contatos);
    }

    @GetMapping("/pesquisar/telefone")
    public ResponseEntity<List<Contato>> pesquisarPorTelefone(@RequestParam("telefone") String telefone) {
        List<Contato> contatos = contatoService.pesquisarPorTelefone(telefone);
        return contatos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contatos);
    }
}
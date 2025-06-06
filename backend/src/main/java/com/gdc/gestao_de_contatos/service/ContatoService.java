package com.gdc.gestao_de_contatos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdc.gestao_de_contatos.entity.Contato;
import com.gdc.gestao_de_contatos.repository.ContatoRepository;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;

    @Autowired
    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Transactional
    public Contato cadastrarContato(Contato contato) {
        // Pode adicionar validações aqui antes de salvar
        return contatoRepository.save(contato);
    }

    @Transactional(readOnly = true)
    public List<Contato> listarTodosContatos() {
        return contatoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Contato> buscarContatoPorId(Long id) {
        return contatoRepository.findById(id);
    }

    @Transactional
    public Optional<Contato> editarContato(Long id, Contato contatoAtualizado) {
        return contatoRepository.findById(id)
                .map(contatoExistente -> {
                    contatoExistente.setNome(contatoAtualizado.getNome());
                    contatoExistente.setTelefone(contatoAtualizado.getTelefone());
                    contatoExistente.setEmail(contatoAtualizado.getEmail());
                    // Adicione outras atualizações de campo conforme necessário
                    return contatoRepository.save(contatoExistente);
                });
    }

    @Transactional
    public boolean excluirContato(Long id) {
        if (contatoRepository.existsById(id)) {
            contatoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Contato> pesquisarContatos(String termo) {
        // Exemplo de pesquisa simples em múltiplos campos.
        // Você pode tornar isso mais sofisticado conforme necessário.
        List<Contato> porNome = contatoRepository.findByNomeContainingIgnoreCase(termo);
        if (!porNome.isEmpty()) {
            return porNome;
        }
        List<Contato> porEmail = contatoRepository.findByEmailContainingIgnoreCase(termo);
        if (!porEmail.isEmpty()) {
            return porEmail;
        }
        return contatoRepository.findByTelefoneContainingIgnoreCase(termo);
    }

     // Métodos de pesquisa mais específicos, se preferir chamá-los diretamente do controller
    @Transactional(readOnly = true)
    public List<Contato> pesquisarPorNome(String nome) {
        return contatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public List<Contato> pesquisarPorEmail(String email) {
        return contatoRepository.findByEmailContainingIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public List<Contato> pesquisarPorTelefone(String telefone) {
        return contatoRepository.findByTelefoneContainingIgnoreCase(telefone);
    }
}
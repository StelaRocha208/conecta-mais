package com.gdc.gestao_de_contatos.service;

import com.gdc.gestao_de_contatos.entity.Contato;
import com.gdc.gestao_de_contatos.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Teste unitário
// Assets utilizados: assertNotNull, assertEquals, assertTrue / assertFalse, verify(...)
// Anotações do JUnit e Mockito usadas: @Test, @BeforeEach, @Mock, @InjectMocks, @ExtendWith(MockitoExtension.class)

@ExtendWith(MockitoExtension.class) // Indica que o teste usará a extensão do Mockito para JUnit 5
class ContatoServiceTest {

    @Mock // Cria um mock do repositório
    private ContatoRepository contatoRepository;

    @InjectMocks // Injeta o mock no service que será testado
    private ContatoService contatoService;

    private Contato contato;

    @BeforeEach // Executa antes de cada teste
    void setUp() {
        contato = new Contato(1L, "João Silva", "11999999999", "joao@email.com"); // Objeto de exemplo
    }

    @Test
    void testCadastrarContato() {
        // Define o comportamento simulado do método save
        when(contatoRepository.save(contato)).thenReturn(contato);

        // Chama o método a ser testado
        Contato salvo = contatoService.cadastrarContato(contato);

        // Verifica se o retorno não é nulo
        assertNotNull(salvo); // Assert 1
        // Verifica se o nome do contato salvo está correto
        assertEquals("João  ", salvo.getNome()); // Assert 2
        // Verifica se o método save foi chamado uma vez
        verify(contatoRepository, times(1)).save(contato); // Assert 3
    }

    @Test
    void testBuscarContatoPorId() {
        // Simula retorno do findById
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        // Executa o método a ser testado
        Optional<Contato> encontrado = contatoService.buscarContatoPorId(1L);

        // Verifica se o contato foi encontrado
        assertTrue(encontrado.isPresent()); // Assert 4
        // Verifica se o email do contato está correto
        assertEquals("joao@email.com", encontrado.get().getEmail()); // Extra
    }

    @Test
    void testEditarContato() {
        // Novo objeto com dados atualizados
        Contato atualizado = new Contato(null, "João Atualizado", "11911111111", "joao@novo.com");

        // Simula retorno do findById
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        // Simula salvamento do contato editado
        when(contatoRepository.save(any(Contato.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Executa a edição
        Optional<Contato> editado = contatoService.editarContato(1L, atualizado);

        // Verificações dos dados atualizados
        assertTrue(editado.isPresent());
        assertEquals("João Atualizado", editado.get().getNome());
        assertEquals("11911111111", editado.get().getTelefone());
    }

    @Test
    void testExcluirContato() {
        // Simula que o contato existe
        when(contatoRepository.existsById(1L)).thenReturn(true);
        // Define que deleteById não faz nada (void)
        doNothing().when(contatoRepository).deleteById(1L);

        // Executa a exclusão
        boolean resultado = contatoService.excluirContato(1L);

        // Verifica se o resultado foi positivo
        assertTrue(resultado);
        // Verifica se o método deleteById foi chamado corretamente
        verify(contatoRepository).deleteById(1L);
    }

    @Test
    void testListarTodosContatos() {
        // Simula retorno com uma lista contendo o contato
        List<Contato> lista = Arrays.asList(contato);
        when(contatoRepository.findAll()).thenReturn(lista);

        // Executa o método
        List<Contato> resultado = contatoService.listarTodosContatos();

        // Verifica se a lista não está vazia
        assertFalse(resultado.isEmpty());
        // Verifica o tamanho da lista
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarContatoInexistente() {
        // Simula que o contato não existe
        when(contatoRepository.findById(2L)).thenReturn(Optional.empty());

        // Executa a busca
        Optional<Contato> resultado = contatoService.buscarContatoPorId(2L);

        // Verifica se o resultado está vazio
        assertFalse(resultado.isPresent());
    }
}



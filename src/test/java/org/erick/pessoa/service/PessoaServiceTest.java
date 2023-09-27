package org.erick.pessoa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.erick.pessoa.domain.Endereco;
import org.erick.pessoa.domain.Pessoa;
import org.erick.pessoa.dto.EnderecoDTO;
import org.erick.pessoa.dto.PessoaDTO;
import org.erick.pessoa.exceptions.PessoaException;
import org.erick.pessoa.repository.EnderecoRepository;
import org.erick.pessoa.repository.PessoaEnderecoRepository;
import org.erick.pessoa.repository.PessoaRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = PessoaService.class)
public class PessoaServiceTest {

	@Autowired
	private PessoaService pessoaService;
    @MockBean
    private PessoaRepository pessoaRepository;
    @MockBean
    private EnderecoRepository enderecoRepository;
    @MockBean
	private PessoaEnderecoRepository pessoaEnderecoRepository;
	
	@Test
	public void deveCriarUmaPessoa() {
        prepararMassaDadosPessoa();
        validarDataNascimentoCriarPessoa();
        validarCriarPessoaCompleta();
	}
	
	@Test
	public void deveEditarUmaPessoa() {
		prepararMassaDadosPessoa();
		validarEditarPessoaCompleta();
	}

	@Test
	public void deveConsultarUmaPessoa() {
		prepararMassaDadosConsultarPessoa();
		validarConsultarIdInvalido();
		validarConsultarPessoaNaoEncontrada();
		validarConsultarPessoa();
	}

	@Test
	public void deveListarPessoas() {
		prepararMassaDadosListarPessoas();
		validarListagemPessoas();
	}

	@Test
	public void deveCriarEnderecoParaPessoa() {
		prepararMassaDadosCriarEndereco(criarEndereco());
		validarPessoaCriacaoEndereco(criarEnderecoDTO());
		validarCriacaoEndereco(criarEnderecoDTO());
	}

	@Test
	public void deveListarEnderecosDaPessoa() {
		prepararMassaDadosListarEnderecoPessoas();
		validarParametroPessoa();
		validarListagemEnderecosPorIdPessoa(1L);
	}

	@Test
	public void deveConfigurarEnderecoPrincipalPessoa() {
		prepararMassaDadosConfigurarEnderecoPrincipalPessoa();
		validarPessoaEnderecoPrincipal();
	}

	private void validarPessoaEnderecoPrincipal() {
		Pessoa endereco = pessoaService.configurarEnderecoPrincipalPessoa(1L, 2L);
		assertTrue(endereco != null);
	}

	private void prepararMassaDadosConfigurarEnderecoPrincipalPessoa() {
		doReturn(Optional.of(criarEndereco())).when(enderecoRepository).findById(any());
		doReturn(Optional.of(criarPessoa(null))).when(pessoaRepository).findById(any());
		doReturn(criarPessoa(null)).when(pessoaRepository).save(any());
	}

	private void validarDataNascimentoCriarPessoa() {
		Exception pessoaException = assertThrows(PessoaException.class, () -> {
			pessoaService.criarPessoa(new PessoaDTO(1L, "Nome 1", "26/09/23", null));
		});
		assertEquals(pessoaException.getMessage(), "Data Invalida. Formato esperado dd/MM/yyyy");
	}
	
	private void validarCriarPessoaCompleta() {
		Pessoa pessoaCriada = pessoaService.criarPessoa(new PessoaDTO(1L, "Nome 1", "26/09/2023", null));
		assertTrue(pessoaCriada != null);
	}

	private void prepararMassaDadosPessoa() {
		doReturn(criarPessoa(null)).when(pessoaRepository).save(any());
	}

	private Pessoa criarPessoa(List<Endereco> enderecos) {
		LocalDateTime dataNascimento = LocalDateTime.parse("26/09/2023 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		return new Pessoa("Pessoa 1", dataNascimento, criarEndereco());
	}

	private Endereco criarEndereco() {
		return new Endereco("Rua 1", "11111", "260-B", "Cidade 1");
	}
	
	private void validarEditarPessoaCompleta() {
		Pessoa pessoaEditada = pessoaService.editarPessoa(new PessoaDTO(1L, "Nome 1", "26/09/2023", null));
		assertTrue(pessoaEditada != null);
	}
	
	private void validarConsultarPessoaNaoEncontrada() {
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
        	pessoaService.consultarPessoaPorId(2L);
        });
		assertEquals(exception.getMessage(), "No row with the given identifier exists: [Pessoa não encontrada#2]");
	}

	private void validarConsultarPessoa() {
		Pessoa pessoa = pessoaService.consultarPessoaPorId(1L);
		assertTrue(pessoa != null);
	}

	private void validarConsultarIdInvalido() {
		Exception pessoaException = assertThrows(PessoaException.class, () -> {
        	pessoaService.consultarPessoaPorId(null);
        });
		assertEquals(pessoaException.getMessage(), "Consulta Inválida. O campo ID é obrigatório");
	}

	private void prepararMassaDadosConsultarPessoa() {
		Pessoa pessoa = criarPessoa(Arrays.asList(criarEndereco()));
		doReturn(Optional.of(pessoa)).when(pessoaRepository).findById(1L);
	}
	
	private void validarListagemPessoas() {
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		assertTrue(pessoas != null && pessoas.size() > 0);
	}

	private void prepararMassaDadosListarPessoas() {
		doReturn(Arrays.asList(criarPessoa(Arrays.asList(criarEndereco())), criarPessoa(Arrays.asList(criarEndereco()))))
		.when(pessoaRepository)
		.findAll();
	}
	
	private void prepararMassaDadosCriarEndereco(Endereco endereco) {
		doReturn(endereco).when(enderecoRepository).save(any());
		Pessoa pessoa = criarPessoa(Arrays.asList(endereco));
		doReturn(Optional.of(pessoa)).when(pessoaRepository).findById(1L);
	}

	private void validarPessoaCriacaoEndereco(EnderecoDTO endereco) {
		Exception pessoaException = assertThrows(PessoaException.class, () -> {
			pessoaService.criarEndereco(endereco);
		});
		assertEquals(pessoaException.getMessage(), "Endeço Inválido. O campo Pessoa é obrigatório");
	}
	
	private EnderecoDTO criarEnderecoDTO() {
		return new EnderecoDTO(null, "Rua 1", "11111", "210", "Cidade 1");
	}
	
	private void validarCriacaoEndereco(EnderecoDTO enderecoDTO) {
		enderecoDTO.setIdPessoa(1L);
		Endereco enderecoCriado = pessoaService.criarEndereco(enderecoDTO);
		assertTrue(enderecoCriado != null);
	}
	
	private void validarParametroPessoa() {
		Exception pessoaException = assertThrows(PessoaException.class, () -> {
			pessoaService.listarEnderecoPorIdPessoa(null);
		});
		assertEquals(pessoaException.getMessage(), "Consulta Inválida. O campo Pessoa é obrigatório");
	}

	private void validarListagemEnderecosPorIdPessoa(Long idPessoa) {
		List<Endereco> enderecosPessoa = pessoaService.listarEnderecoPorIdPessoa(idPessoa);
		assertTrue(enderecosPessoa != null && enderecosPessoa.size() > 0);
	}

	private void prepararMassaDadosListarEnderecoPessoas() {
		doReturn(Arrays.asList(criarEndereco(), criarEndereco())).when(pessoaEnderecoRepository).listarEnderecosPorIdPessoa(1L);
	}
}
